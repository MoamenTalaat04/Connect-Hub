package com.connecthub.service.impl;

import com.connecthub.model.Posts;
import com.connecthub.model.User;
import com.connecthub.repository.ContentRepository;
import com.connecthub.repository.UserRepository;
import com.connecthub.service.ProfileService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DefaultProfileService implements ProfileService {
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public DefaultProfileService(UserRepository userRepository, ContentRepository contentRepository) {
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    public Optional<User> getProfile(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<Posts> fetchPostsFromUser(String userId) {
        List<Posts> posts = new ArrayList<>();
        for (Posts post : contentRepository.findAllPosts()) {
            if (userId.equals(post.getAuthorId())) {
                posts.add(post);
            }
        }
        posts.sort(Comparator.comparing(Posts::getTimestamp).reversed());
        return posts;
    }

    @Override
    public void updateBio(String userId, String newBio) {
        mutateUser(userId, user -> user.setBio(newBio));
    }

    @Override
    public void updateProfilePhoto(String userId, String path) {
        mutateUser(userId, user -> user.setProfilePhotoPath(path));
    }

    @Override
    public void updateCoverPhoto(String userId, String path) {
        mutateUser(userId, user -> user.setCoverPhotoPath(path));
    }

    private void mutateUser(String userId, java.util.function.Consumer<User> mutation) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (userId.equals(user.getUserId())) {
                mutation.accept(user);
                break;
            }
        }
        userRepository.saveAll(users);
    }
}
