package com.connecthub.service;

import com.connecthub.model.Posts;
import com.connecthub.model.Stories;
import com.connecthub.model.User;

import java.util.List;

public interface NewsFeedService {
    void addPost(String currentUserId, String content, String imagePath);

    void addStory(String currentUserId, String content, String imagePath);

    List<Posts> fetchPostsFromFriends(String currentUserId);

    List<Stories> fetchStoriesFromFriends(String currentUserId);

    List<User> suggestFriends(String currentUserId);

    List<String> fetchFriendStatus(String currentUserId);
}
