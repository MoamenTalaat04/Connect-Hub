package com.connecthub.service;

import com.connecthub.model.Group;
import com.connecthub.model.User;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    Group createGroup(String ownerId, String name, String description);

    boolean joinGroup(String groupId, String userId);

    boolean leaveGroup(String groupId, String userId);

    Optional<Group> getGroup(String groupId);

    List<User> getMembers(String groupId);

    boolean addPost(String groupId, String authorId, String content, String imagePath);
}
