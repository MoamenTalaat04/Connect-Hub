package com.connecthub.service.impl;

import com.connecthub.events.DomainEvent;
import com.connecthub.events.EventBus;
import com.connecthub.model.Posts;
import com.connecthub.model.Stories;
import com.connecthub.model.User;
import com.connecthub.repository.ContentRepository;
import com.connecthub.repository.UserRepository;
import com.connecthub.service.FriendService;
import com.connecthub.service.NewsFeedService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DefaultNewsFeedService implements NewsFeedService {
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final FriendService friendService;
    private final EventBus eventBus;

    public DefaultNewsFeedService(ContentRepository contentRepository,
                                  UserRepository userRepository,
                                  FriendService friendService,
                                  EventBus eventBus) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
        this.friendService = friendService;
        this.eventBus = eventBus;
    }

    @Override
    public void addPost(String currentUserId, String content, String imagePath) {
        String id = String.valueOf(contentRepository.findAllPosts().size() + 1);
        contentRepository.addPost(new Posts(content, currentUserId, id, imagePath, LocalDateTime.now()));
        HashMap<String, String> payload = new HashMap<>();
        payload.put("content", content);
        eventBus.publish(new DomainEvent("post.created", currentUserId, payload));
    }

    @Override
    public void addStory(String currentUserId, String content, String imagePath) {
        String id = String.valueOf(contentRepository.findActiveStories().size() + 1);
        contentRepository.addStory(new Stories(content, currentUserId, id, imagePath, LocalDateTime.now()));
    }

    @Override
    public List<Posts> fetchPostsFromFriends(String currentUserId) {
        User current = userRepository.findById(currentUserId).orElse(null);
        if (current == null) {
            return new ArrayList<>();
        }
        List<Posts> friendsPosts = new ArrayList<>();
        List<Posts> allPosts = contentRepository.findAllPosts();
        for (String friendId : current.getFriends()) {
            for (Posts post : allPosts) {
                if (friendId.equals(post.getAuthorId())) {
                    friendsPosts.add(post);
                }
            }
        }
        friendsPosts.sort(Comparator.comparing(Posts::getTimestamp).reversed());
        return friendsPosts;
    }

    @Override
    public List<Stories> fetchStoriesFromFriends(String currentUserId) {
        User current = userRepository.findById(currentUserId).orElse(null);
        if (current == null) {
            return new ArrayList<>();
        }
        List<Stories> friendsStories = new ArrayList<>();
        List<Stories> allStories = contentRepository.findActiveStories();
        for (String friendId : current.getFriends()) {
            for (Stories story : allStories) {
                if (friendId.equals(story.getAuthorId())) {
                    friendsStories.add(story);
                }
            }
        }
        friendsStories.sort(Comparator.comparing(Stories::getTimestamp).reversed());
        return friendsStories;
    }

    @Override
    public List<User> suggestFriends(String currentUserId) {
        return friendService.suggestions(currentUserId);
    }

    @Override
    public List<String> fetchFriendStatus(String currentUserId) {
        return friendService.friendStatus(currentUserId);
    }
}
