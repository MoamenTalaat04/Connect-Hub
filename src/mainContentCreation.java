import com.github.cliftonlabs.json_simple.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.*;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
public class mainContentCreation {
    private final String postsFilePath = "posts.json";
    private final String storiesFilePath = "stories.json";
    public void createPost(String authorId,String content, String imagePath )
    {
        try {
            Posts post = new Posts(content, authorId, UUID.randomUUID().toString(), imagePath, LocalDateTime.now());
            saveContentToFile(post, "posts.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void createStory(String authorID,String content,String imagePath)
    {
        try {
            Stories story = new Stories(content,authorID,UUID.randomUUID().toString(),imagePath,LocalDateTime.now());
            saveContentToFile(story,"stories.json");
        } catch (Exception e) {
                throw new RuntimeException(e);
        }

    }

    private void saveContentToFile(Content content, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Content> c = readContentFromFile(filePath,Content[].class);
        c.add(content);
        mapper.writeValue(new File(filePath), c);
    }

    private <T> List<T> readContentFromFile(String filePath,Class<T[]> X) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(filePath);
            if (!file.exists()) return new ArrayList<>();
            T[] contentArray = mapper.readValue(file, X);
            return new ArrayList<>(Arrays.asList(contentArray));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void deleteExpiredStory ()
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Stories> stories = readContentFromFile(storiesFilePath, Stories[].class);
            List<Stories> updatedStories = new ArrayList<>();
            for (Stories story : stories) {
                if (!story.isExpired()) {
                    updatedStories.add(story);
                }
            }
            mapper.writeValue(new File(storiesFilePath), updatedStories);
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }
    public List<Posts> readPosts()  {
        try {
            return readContentFromFile(postsFilePath, Posts[].class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    public List<Stories> readStories() {
        try {
            List<Stories> stories = readContentFromFile(storiesFilePath, Stories[].class);
            List<Stories> active = new ArrayList<>();
            for(Stories story:stories)
            {
                if(!story.isExpired())
                {
                    active.add(story);
                }
            }
            return active;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
