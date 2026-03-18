package com.connecthub.repository;

import com.connecthub.model.Posts;
import com.connecthub.model.Stories;

import java.util.List;

public interface ContentRepository {
    List<Posts> findAllPosts();

    List<Stories> findActiveStories();

    void addPost(Posts post);

    void addStory(Stories story);
}
