package com.connecthub.repository.json;

import com.connecthub.model.Posts;
import com.connecthub.model.Stories;
import com.connecthub.repository.ContentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonContentRepository implements ContentRepository {
    private final String postsFile;
    private final String storiesFile;
    private final ObjectMapper mapper;

    public JsonContentRepository(String postsFile, String storiesFile) {
        this.postsFile = postsFile;
        this.storiesFile = storiesFile;
        this.mapper = JsonStoreSupport.createMapper();
    }

    @Override
    public List<Posts> findAllPosts() {
        return JsonStoreSupport.readList(mapper, postsFile, Posts[].class);
    }

    @Override
    public List<Stories> findActiveStories() {
        List<Stories> stories = JsonStoreSupport.readList(mapper, storiesFile, Stories[].class);
        List<Stories> active = new ArrayList<>();
        for (Stories story : stories) {
            if (!story.isExpired()) {
                active.add(story);
            }
        }
        JsonStoreSupport.writeList(mapper, storiesFile, active);
        return active;
    }

    @Override
    public void addPost(Posts post) {
        List<Posts> posts = new ArrayList<>(findAllPosts());
        posts.add(post);
        JsonStoreSupport.writeList(mapper, postsFile, posts);
    }

    @Override
    public void addStory(Stories story) {
        List<Stories> stories = new ArrayList<>(findActiveStories());
        story.setExpired(LocalDateTime.now().isAfter(story.getTimestamp().plusHours(24)));
        stories.add(story);
        JsonStoreSupport.writeList(mapper, storiesFile, stories);
    }
}
