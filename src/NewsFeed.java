import java.io.IOException;
import java.util.*;

public class NewsFeed {
    private FriendManagement friendManagement;
    private MainContentCreation contentCreation;
    private User currentUser;
    private UserDatabase userDatabase;



    public NewsFeed(User currentUser,ArrayList<User>allUsers) {
        this.friendManagement =new FriendManagement(currentUser,allUsers); ;
        this.contentCreation = new MainContentCreation();
        this.currentUser = currentUser;
        this.userDatabase = UserDatabase.getInstance();
    }

    public void addPost(String content, String imagePath) {
        try {
            contentCreation.createPost(currentUser.getUserId(), content, imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addStory(String content, String imagePath) {
        try {
            contentCreation.createStory(currentUser.getUserId(), content, imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Fetch posts from friends
    public ArrayList<Posts> fetchPostsFromFriends() {
        ArrayList<Posts> friendPosts = new ArrayList<>();
        ArrayList<Posts> allPosts = null;
        try {
            allPosts = contentCreation.readPosts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Filter posts to include only those from friends
        for (String friend : currentUser.getFriends()) {
            for (Posts post : allPosts) {
                if (post.getAuthorId().equals(friend)) {
                    friendPosts.add(post);
                }
            }
        }
        // Sort posts by timestamp (latest first)
        friendPosts.sort(Comparator.comparing(Posts::getTimestamp).reversed());
        return friendPosts;
    }

    // Fetch stories from friends
    public ArrayList<Stories> fetchStoriesFromFriends() {
        ArrayList<Stories> friendStories = new ArrayList<>();
        ArrayList<Stories> allStories = null;
        try {
            allStories = contentCreation.readActiveStories();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Filter stories to include only those from friends
        for (String friend : currentUser.getFriends()) {
            for (Stories story : allStories) {
                if (story.getAuthorId().equals(friend)) {
                    friendStories.add(story);
                }
            }
        }
        // Sort stories by timestamp (latest first)
        friendStories.sort(Comparator.comparing(Stories::getTimestamp).reversed());
        return friendStories;
    }

    // Suggest friends to the current user
    public ArrayList<User> suggestFriends() {
        return friendManagement.suggestFriends();  // Using FriendManagement to suggest friends
    }
    public ArrayList<String> fetchFriendStatus() {
        return friendManagement.FriendStatus();  // Using FriendManagement to fetch friend status
    }

    public String getUsernameByID(String UserID){
        for(User user :friendManagement.getUsersById( currentUser.getFriends())){
            if (user.getUserId().equals(UserID)){
                return user.getUsername();
            }
        }
        return null;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public FriendManagement getFriendManagement() {
        return friendManagement;
    }
    public UserDatabase getUserDatabase() {
        return userDatabase;
    }
}
