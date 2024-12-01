import java.util.*;

public class Newsfeed {
    private Map<String, User> usersMap;

    public Newsfeed(Map<String, User> usersMap) {
        this.usersMap = usersMap;
    }

    // Add a post for a user
    public void addPost(User user, String content) {
        if (user == null) {
            System.out.println("User does not exist.");
            return;
        }

        Post post = new Post(user.getUserId(), content);
        user.addPost(post);
        System.out.println("Post added for " + user.getUserId() + ": " + content);
    }

    // Add a story for a user
    public void addStory(User user, String content) {
        if (user == null) {
            System.out.println("User does not exist.");
            return;
        }

        Story story = new Story(user.getUserId(), content);
        user.addStory(story);
        System.out.println("Story added for " + user.getUserId() + ": " + content);
    }

    // Fetch posts from friends for a user
    public List<Post> fetchPostsFromFriends(User user) {
        if (user == null) {
            System.out.println("User does not exist.");
            return Collections.emptyList();
        }

        List<Post> newsfeed = new ArrayList<>();
        // Fetch posts from friends
        for (String friendId : user.getFriends()) {
            User friend = usersMap.get(friendId);
            if (friend != null) {
                newsfeed.addAll(friend.getPosts());
            }
        }

        // Sort posts by most recent timestamp
        newsfeed.sort(Comparator.comparing(Post::getTimestamp).reversed());

        return newsfeed;
    }

    // Fetch stories from friends for a user
    public List<Story> fetchStoriesFromFriends(User user) {
        if (user == null) {
            System.out.println("User does not exist.");
            return Collections.emptyList();
        }

        List<Story> newsfeed = new ArrayList<>();
        // Fetch stories from friends
        for (String friendId : user.getFriends()) {
            User friend = usersMap.get(friendId);
            if (friend != null) {
                for (Story story : friend.getStories()) {
                    if (!story.isExpired()) { // Only show non-expired stories
                        newsfeed.add(story);
                    }
                }
            }
        }

        // Sort stories by most recent timestamp
        newsfeed.sort(Comparator.comparing(Story::getTimestamp).reversed());

        return newsfeed;
    }

    // Display posts and stories for a user
    public void displayNewsfeed(User user) {
        List<Post> posts = fetchPostsFromFriends(user);
        List<Story> stories = fetchStoriesFromFriends(user);

        System.out.println("Newsfeed for " + user.getUserId() + ":");
        System.out.println("Posts:");
        for (Post post : posts) {
            System.out.println(post);
        }

        System.out.println("Stories:");
        for (Story story : stories) {
            System.out.println(story);
        }
    }

   }
