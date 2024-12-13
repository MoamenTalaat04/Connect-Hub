import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public Posts createPost(String authorId, String content, String imagePath) throws IOException {
        Posts post = new Posts(content, authorId, getNewPostId(), imagePath, LocalDateTime.now());
        saveContentToFile(post, postsFilePath);
        return post;
    }

    public void createStory(String authorId, String content, String imagePath) throws IOException {
        Stories story = new Stories(content, authorId, getNewStoryId(), imagePath, LocalDateTime.now());
        saveContentToFile(story, storiesFilePath);
    }

    private void saveContentToFile(Content content, String filePath) throws IOException {
        ArrayList<Content> contents = readContentFromFile(filePath, Content[].class);
        contents.add(content);
        mapper.writeValue(new File(filePath), contents);
    }
    public void saveContentToFile(ArrayList<Posts> posts) throws IOException {
        mapper.writeValue(new File(postsFilePath), posts);
    }

    private <T> ArrayList<T> readContentFromFile(String filePath, Class<T[]> type) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            mapper.writeValue(file, new ArrayList<>());
            return new ArrayList<>();
        }
        T[] contentArray = mapper.readValue(file, type);
        return new ArrayList<>(Arrays.asList(contentArray));
    }

    public void deleteExpiredStories() throws IOException {
        ArrayList<Stories> stories = readContentFromFile(storiesFilePath, Stories[].class);
        ArrayList<Stories> activeStories = new ArrayList<>();
        for (Stories story : stories) {
            if (!story.isExpired()) {
                activeStories.add(story);
            }
        }
        mapper.writeValue(new File(storiesFilePath), activeStories);
    }

    public ArrayList<Posts> readPosts() throws IOException {
        return readContentFromFile(postsFilePath, Posts[].class);
    }

    public ArrayList<Stories> readActiveStories() throws IOException {
        try {
            deleteExpiredStories();
            return readContentFromFile(storiesFilePath,Stories[].class);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
    public void saveContentToFile(ArrayList<Posts> posts) throws IOException {
        mapper.writeValue(new File(postsFilePath),posts);
    }
}
