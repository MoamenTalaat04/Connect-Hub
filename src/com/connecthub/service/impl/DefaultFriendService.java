package com.connecthub.service.impl;

import com.connecthub.events.DomainEvent;
import com.connecthub.events.EventBus;
import com.connecthub.model.User;
import com.connecthub.repository.UserRepository;
import com.connecthub.service.FriendService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultFriendService implements FriendService {
    private final UserRepository userRepository;
    private final EventBus eventBus;

    public DefaultFriendService(UserRepository userRepository, EventBus eventBus) {
        this.userRepository = userRepository;
        this.eventBus = eventBus;
    }

    @Override
    public boolean sendFriendRequest(String currentUserId, String receiverId) {
        List<User> users = users();
        User current = byId(users, currentUserId);
        User receiver = byId(users, receiverId);
        if (current == null || receiver == null) {
            return false;
        }
        if (current.getFriends().contains(receiverId) || receiver.getBlocked().contains(currentUserId)) {
            return false;
        }
        if (!receiver.getPendingRequests().contains(currentUserId)) {
            receiver.getPendingRequests().add(currentUserId);
        }
        userRepository.saveAll(users);
        Map<String, String> payload = new HashMap<>();
        payload.put("receiverId", receiverId);
        eventBus.publish(new DomainEvent("friend.request.sent", currentUserId, payload));
        return true;
    }

    @Override
    public boolean acceptFriendRequest(String currentUserId, String senderId) {
        List<User> users = users();
        User current = byId(users, currentUserId);
        User sender = byId(users, senderId);
        if (current == null || sender == null || !current.getPendingRequests().contains(senderId)) {
            return false;
        }
        current.getPendingRequests().remove(senderId);
        if (!current.getFriends().contains(senderId)) {
            current.getFriends().add(senderId);
        }
        if (!sender.getFriends().contains(currentUserId)) {
            sender.getFriends().add(currentUserId);
        }
        userRepository.saveAll(users);
        return true;
    }

    @Override
    public boolean rejectFriendRequest(String currentUserId, String senderId) {
        List<User> users = users();
        User current = byId(users, currentUserId);
        if (current == null) {
            return false;
        }
        boolean removed = current.getPendingRequests().remove(senderId);
        if (removed) {
            userRepository.saveAll(users);
        }
        return removed;
    }

    @Override
    public boolean cancelFriendRequest(String currentUserId, String receiverId) {
        List<User> users = users();
        User receiver = byId(users, receiverId);
        if (receiver == null) {
            return false;
        }
        boolean removed = receiver.getPendingRequests().remove(currentUserId);
        if (removed) {
            userRepository.saveAll(users);
        }
        return removed;
    }

    @Override
    public boolean removeFriend(String currentUserId, String friendId) {
        List<User> users = users();
        User current = byId(users, currentUserId);
        User friend = byId(users, friendId);
        if (current == null || friend == null) {
            return false;
        }
        boolean a = current.getFriends().remove(friendId);
        boolean b = friend.getFriends().remove(currentUserId);
        userRepository.saveAll(users);
        return a && b;
    }

    @Override
    public boolean blockUser(String currentUserId, String blockedId) {
        List<User> users = users();
        User current = byId(users, currentUserId);
        if (current == null) {
            return false;
        }
        if (!current.getBlocked().contains(blockedId)) {
            current.getBlocked().add(blockedId);
        }
        removeFriend(currentUserId, blockedId);
        userRepository.saveAll(users);
        return true;
    }

    @Override
    public boolean unblockUser(String currentUserId, String unblockedId) {
        List<User> users = users();
        User current = byId(users, currentUserId);
        if (current == null) {
            return false;
        }
        boolean removed = current.getBlocked().remove(unblockedId);
        if (removed) {
            userRepository.saveAll(users);
        }
        return removed;
    }

    @Override
    public List<User> suggestions(String currentUserId) {
        List<User> users = users();
        User current = byId(users, currentUserId);
        if (current == null) {
            return Collections.emptyList();
        }
        List<User> suggestions = new ArrayList<>(users);
        suggestions.removeIf(user -> user.getUserId().equals(currentUserId)
                || current.getFriends().contains(user.getUserId())
                || current.getBlocked().contains(user.getUserId()));
        return suggestions;
    }

    @Override
    public List<User> friends(String currentUserId) {
        User current = userRepository.findById(currentUserId).orElse(null);
        if (current == null) {
            return Collections.emptyList();
        }
        List<User> users = users();
        List<User> friends = new ArrayList<>();
        for (String friendId : current.getFriends()) {
            User friend = byId(users, friendId);
            if (friend != null) {
                friends.add(friend);
            }
        }
        return friends;
    }

    @Override
    public List<User> receivedRequests(String currentUserId) {
        User current = userRepository.findById(currentUserId).orElse(null);
        if (current == null) {
            return Collections.emptyList();
        }
        List<User> users = users();
        List<User> requests = new ArrayList<>();
        for (String requestId : current.getPendingRequests()) {
            User requester = byId(users, requestId);
            if (requester != null) {
                requests.add(requester);
            }
        }
        return requests;
    }

    @Override
    public List<User> sentRequests(String currentUserId) {
        List<User> sent = new ArrayList<>();
        for (User user : users()) {
            if (user.getPendingRequests().contains(currentUserId)) {
                sent.add(user);
            }
        }
        return sent;
    }

    @Override
    public List<String> friendStatus(String currentUserId) {
        List<String> statuses = new ArrayList<>();
        for (User friend : friends(currentUserId)) {
            statuses.add(friend.getUsername() + " is " + friend.getStatus());
        }
        return statuses;
    }

    private List<User> users() {
        return new ArrayList<>(userRepository.findAll());
    }

    private User byId(List<User> users, String id) {
        Optional<User> found = users.stream().filter(user -> id.equals(user.getUserId())).findFirst();
        return found.orElse(null);
    }
}
