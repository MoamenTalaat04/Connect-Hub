import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProfileManager {
    private User currentUser ;
    private MainContentCreation contentCreation;
    private UserDatabase userDatabase;
    private ArrayList<User> allUsers;

    public ProfileManager(User currentUser) {
        this.currentUser = currentUser;
        contentCreation=new MainContentCreation();
        userDatabase=UserDatabase.getInstance();
        allUsers=userDatabase.readUsersFromFile();
    }

    public User getProfile(String userId) {
        ArrayList<User> profiles = allUsers;
        for (User profile : profiles) {
            if (profile.getUserId().equals(userId)) {
                return profile;
            }
        }
        return null;
    }

    public ArrayList<Posts> fetchPostsFromUser() {
        fetchAllUsers();
        ArrayList<Posts> userposts = new ArrayList<>();
        ArrayList<Posts> allPosts = null;
        try {
            allPosts = contentCreation.readPosts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Filter posts to include only those from friends
            for (Posts post : allPosts) {
                if (post.getAuthorId().equals(currentUser.getUserId())) {
                    userposts.add(post);
                }

        }
        // Sort posts by timestamp (latest first)
        userposts.sort(Comparator.comparing(Posts::getTimestamp).reversed());
        return userposts;
    }
    public ArrayList<User>getAllUsers(){
        return allUsers;
    }
    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public void fetchAllUsers(){
        setAllUsers(userDatabase.readUsersFromFile());
        setCurrentUser(getProfile(currentUser.getUserId()));
    }
}    