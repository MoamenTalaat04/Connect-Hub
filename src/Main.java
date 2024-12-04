import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        MainContentCreation mainContentCreation = new MainContentCreation();
        mainContentCreation.createPost("1", "Hello World", "image1.jpg");
        mainContentCreation.createPost("2", "Hello World 2", "image2.jpg");
        mainContentCreation.createStory("1", "Hello World", "image1.jpg");
        mainContentCreation.createStory("2", "Hello World 2", "image2.jpg");
        List<Posts> posts = mainContentCreation.readPosts();
        List<Stories> stories = mainContentCreation.readActiveStories();
        System.out.println("Posts:");
        for (Posts post : posts) {
            System.out.println("Content: " + post.getContent());
            System.out.println("Author ID: " + post.getAuthorId());
            System.out.println("Content ID: " + post.getContentId());
            System.out.println("Image Path: " + post.getImagePath());
            System.out.println("Timestamp: " + post.getTimestamp());
            System.out.println();
        }
        System.out.println("Stories:");
        for (Stories story : stories) {
            System.out.println("Content: " + story.getContent());
            System.out.println("Author ID: " + story.getAuthorId());
            System.out.println("Content ID: " + story.getContentId());
            System.out.println("Image Path: " + story.getImagePath());
            System.out.println("Timestamp: " + story.getTimestamp());
            System.out.println("Expired: " + story.isExpired());
            System.out.println();
        }
        mainContentCreation.deleteExpiredStories();

    }
}