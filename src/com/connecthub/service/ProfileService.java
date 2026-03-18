package com.connecthub.service;

import com.connecthub.model.Posts;
import com.connecthub.model.User;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    Optional<User> getProfile(String userId);

    List<Posts> fetchPostsFromUser(String userId);

    void updateBio(String userId, String newBio);

    void updateProfilePhoto(String userId, String path);

    void updateCoverPhoto(String userId, String path);
}
