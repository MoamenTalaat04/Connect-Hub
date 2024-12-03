import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MainContentCreation {
    private final String postsFilePath = "posts.json";
    private final String storiesFilePath = "stories.json";

    private final ObjectMapper mapper ;
    public MainContentCreation() {

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    public String getNewPostId() throws IOException {
        return String.valueOf(readPosts().size()+1);
    }
    public String getNewStoryId() throws IOException {
        return String.valueOf(readActiveStories().size()+1);
    }
    public void createPost(String authorId, String content, String imagePath) throws IOException {
        Posts post = new Posts(content, authorId, getNewPostId(), imagePath, LocalDateTime.now());
        saveContentToFile(post, postsFilePath);
    }

    public void createStory(String authorId, String content, String imagePath) throws IOException {
        Stories story = new Stories(content, authorId, getNewStoryId(), imagePath, LocalDateTime.now());
        saveContentToFile(story, storiesFilePath);
    }

    private void saveContentToFile(Content content, String filePath) throws IOException {
        List<Content> contents = readContentFromFile(filePath, Content[].class);
        contents.add(content);
        mapper.writeValue(new File(filePath), contents);
    }

    private <T> List<T> readContentFromFile(String filePath, Class<T[]> type) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();
        T[] contentArray = mapper.readValue(file, type);
        return new ArrayList<>(Arrays.asList(contentArray));
    }

    public void deleteExpiredStories() throws IOException {
        List<Stories> stories = readContentFromFile(storiesFilePath, Stories[].class);
        List<Stories> activeStories = new ArrayList<>();
        for (Stories story : stories) {
            if (!story.isExpired()) {
                activeStories.add(story);
            }
        }
        mapper.writeValue(new File(storiesFilePath), activeStories);
    }

    public List<Posts> readPosts() throws IOException {
        return readContentFromFile(postsFilePath, Posts[].class);
    }

    public List<Stories> readActiveStories() throws IOException {
        try {
            deleteExpiredStories();
            return readContentFromFile(storiesFilePath,Stories[].class);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
}
