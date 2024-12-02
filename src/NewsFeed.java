import java.util.*;

public class NewsFeed {
    private FriendManagement friendManagement;
    private mainContentCreation contentCreation;

    // Constructor to initialize NewsFeed with current user and FriendManagement instance
    public NewsFeed(FriendManagement friendManagement) {
        this.friendManagement = friendManagement;
        this.contentCreation = new mainContentCreation();
    }

    // Add a new post
    public void addPost(String content, String imagePath) {
        contentCreation.createPost(friendManagement.getCurrentUser().getUserId(), content, imagePath);

    }

    // Add a new story
    public void addStory(String content, String imagePath) {
        contentCreation.createStory(friendManagement.getCurrentUser().getUserId(), content, imagePath);
    }

    // Fetch posts from friends
    public ArrayList<Posts> fetchPostsFromFriends() {
        ArrayList<Posts> friendPosts = new ArrayList<>();
        ArrayList<Posts> allPosts = contentCreation.readPosts();

        // Filter posts to include only those from friends
        for (User friend : friendManagement.getCurrentUser().getFriends()) {
            for (Posts post : allPosts) {
                if (post.getAuthorId().equals(friend.getUserId())) {
                    friendPosts.add(post);
                }
            }
        }

        // Sort posts by timestamp (latest first)
        friendPosts.sort(Comparator.comparing(Posts::getTimestamp).reversed());
        return friendPosts;
    }

    // Fetch stories from friends (using FriendManagement for friend handling)
    public ArrayList<Stories> fetchStoriesFromFriends() {
        ArrayList<Stories> friendStories = new ArrayList<>();
        ArrayList<Stories> allStories = contentCreation.readStories();

        // Filter stories to include only those from friends
        for (User friend : friendManagement.getCurrentUser().getFriends()) {
            for (Stories story : allStories) {
                if (story.getAuthorId().equals(friend.getUserId())) {
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

    // Display the newsfeed
    public void displayNewsFeed() {
        ArrayList<Posts> posts = fetchPostsFromFriends();
        ArrayList<Stories> stories = fetchStoriesFromFriends();

        // Display posts
        System.out.println("Posts:");
        for (Posts post : posts) {
            System.out.println(post.getContent());
        }

        // Display stories
        System.out.println("Stories:");
        for (Stories story : stories) {
            System.out.println(story.getContent());
        }

        // Display friend suggestions
        System.out.println("Friend Suggestions:");
        ArrayList<User> suggestions = suggestFriends();
        for (User suggestion : suggestions) {
            System.out.println(suggestion.getUsername());
        }
    }
}
