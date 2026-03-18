import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainContentCreation {
    private final com.connecthub.repository.ContentRepository contentRepository;

    public MainContentCreation() {
        contentRepository = new com.connecthub.repository.json.JsonContentRepository("posts.json", "stories.json");
    }
    public String getNewPostId() throws IOException {
        return String.valueOf(readPosts().size()+1);
    }
    public String getNewStoryId() throws IOException {
        return String.valueOf(readActiveStories().size()+1);
    }
    public void createPost(String authorId, String content, String imagePath) throws IOException {
        contentRepository.addPost(new com.connecthub.model.Posts(content, authorId, getNewPostId(), imagePath, LocalDateTime.now()));
    }

    public void createStory(String authorId, String content, String imagePath) throws IOException {
        contentRepository.addStory(new com.connecthub.model.Stories(content, authorId, getNewStoryId(), imagePath, LocalDateTime.now()));
    }

    public void deleteExpiredStories() throws IOException {
        contentRepository.findActiveStories();
    }

    public ArrayList<Posts> readPosts() throws IOException {
        ArrayList<Posts> posts = new ArrayList<>();
        for (com.connecthub.model.Posts post : contentRepository.findAllPosts()) {
            posts.add(LegacyMapper.toLegacyPost(post));
        }
        return posts;
    }

    public ArrayList<Stories> readActiveStories() throws IOException {
        ArrayList<Stories> stories = new ArrayList<>();
        try {
            for (com.connecthub.model.Stories story : contentRepository.findActiveStories()) {
                stories.add(LegacyMapper.toLegacyStory(story));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stories;
    }
}
