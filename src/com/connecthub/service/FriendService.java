package com.connecthub.service;

import com.connecthub.model.User;

import java.util.List;

public interface FriendService {
    boolean sendFriendRequest(String currentUserId, String receiverId);

    boolean acceptFriendRequest(String currentUserId, String senderId);

    boolean rejectFriendRequest(String currentUserId, String senderId);

    boolean cancelFriendRequest(String currentUserId, String receiverId);

    boolean removeFriend(String currentUserId, String friendId);

    boolean blockUser(String currentUserId, String blockedId);

    boolean unblockUser(String currentUserId, String unblockedId);

    List<User> suggestions(String currentUserId);

    List<User> friends(String currentUserId);

    List<User> receivedRequests(String currentUserId);

    List<User> sentRequests(String currentUserId);

    List<String> friendStatus(String currentUserId);
}
