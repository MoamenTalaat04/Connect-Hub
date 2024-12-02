import java.util.ArrayList;
import java.util.Comparator;

public class NewsFeed {
    private ArrayList<User> allUsers;
    private mainContentCreation contentCreation;
    private User currentUser;  // The current logged-in user

    // Constructor to initialize NewsFeed with all users and the current user
    public NewsFeed(ArrayList<User> allUsers, User currentUser) {
        this.allUsers = allUsers;
        this.contentCreation = new mainContentCreation();
        this.currentUser = currentUser;
    }

    // Add a new post for a user
    public void addPost(User user, String content, String imagePath) {
        contentCreation.createPost(user.getUserId(), content, imagePath);  // Create post and save
        System.out.println("Post added for " + user.getUserId());
    }

    // Add a new story for a user
    public void addStory(User user, String content, String imagePath) {
        contentCreation.createStory(user.getUserId(), content, imagePath);  // Create story and save
        System.out.println("Story added for " + user.getUserId());
    }

    // Fetch posts from friends of the current user
    public ArrayList<Posts> fetchPostsFromFriends() {
        ArrayList<Posts> friendPosts = new ArrayList<>();
        ArrayList<Posts> allPosts = contentCreation.readPosts();        // Get all posts in the system

        for (User friend : currentUser.getFriends()) {
            for (Posts post : allPosts) {
                if (post.getAuthorId().equals(friend.getUserId())) {
                    friendPosts.add(post);
                }
            }
        }
        friendPosts.sort(Comparator.comparing(Posts::getTimestamp).reversed());     // Sort posts by timestamp (latest first)
        return friendPosts;
    }

    // Fetch stories from friends of the current user
    public ArrayList<Stories> fetchStoriesFromFriends() {
        ArrayList<Stories> friendStories = new ArrayList<>();
        ArrayList<Stories> allStories = contentCreation.readStories();  // Get all active stories in the system

        for (User friend : currentUser.getFriends()) {
            for (Stories story : allStories) {
                if (story.getAuthorId().equals(friend.getUserId())) {
                    friendStories.add(story);
                }
            }
        }
        friendStories.sort(Comparator.comparing(Stories::getTimestamp).reversed());   // Sort stories by timestamp (latest first)
        return friendStories;
    }
}

