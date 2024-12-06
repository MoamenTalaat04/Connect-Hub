import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProfileManager {
    private User currentUser ;
    private MainContentCreation contentCreation;

    public ProfileManager(User currentUser) {
        this.currentUser = currentUser;
        contentCreation=new MainContentCreation();
    }

//    public User getProfile(String userId) {
//        List<User> profiles = userDatabase.readUsersFromFile();
//        return profiles.stream().filter(p -> p.getUserId().equals(userId)).findFirst().orElse(null);
//    }
    public ArrayList<Posts> fetchPostsFromUser() {

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
}    