package com.connecthub.service.impl;

import com.connecthub.events.DomainEvent;
import com.connecthub.events.EventBus;
import com.connecthub.model.Group;
import com.connecthub.model.GroupMember;
import com.connecthub.model.Posts;
import com.connecthub.model.User;
import com.connecthub.repository.GroupRepository;
import com.connecthub.repository.UserRepository;
import com.connecthub.service.GroupService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class DefaultGroupService implements GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final EventBus eventBus;

    public DefaultGroupService(GroupRepository groupRepository, UserRepository userRepository, EventBus eventBus) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.eventBus = eventBus;
    }

    @Override
    public Group createGroup(String ownerId, String name, String description) {
        List<Group> groups = new ArrayList<>(groupRepository.findAll());
        String id = String.valueOf(groups.size() + 1);
        Group group = new Group(id, name, description, ownerId, LocalDateTime.now());
        group.getMembers().add(new GroupMember(ownerId, "OWNER", LocalDateTime.now()));
        groups.add(group);
        groupRepository.saveAll(groups);
        return group;
    }

    @Override
    public boolean joinGroup(String groupId, String userId) {
        List<Group> groups = new ArrayList<>(groupRepository.findAll());
        for (Group group : groups) {
            if (groupId.equals(group.getGroupId())) {
                boolean exists = group.getMembers().stream().anyMatch(m -> userId.equals(m.getUserId()));
                if (!exists) {
                    group.getMembers().add(new GroupMember(userId, "MEMBER", LocalDateTime.now()));
                    groupRepository.saveAll(groups);
                    HashMap<String, String> payload = new HashMap<>();
                    payload.put("groupId", groupId);
                    eventBus.publish(new DomainEvent("group.joined", userId, payload));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean leaveGroup(String groupId, String userId) {
        List<Group> groups = new ArrayList<>(groupRepository.findAll());
        for (Group group : groups) {
            if (groupId.equals(group.getGroupId())) {
                boolean removed = group.getMembers().removeIf(member -> userId.equals(member.getUserId()));
                if (removed) {
                    groupRepository.saveAll(groups);
                }
                return removed;
            }
        }
        return false;
    }

    @Override
    public Optional<Group> getGroup(String groupId) {
        return groupRepository.findById(groupId);
    }

    @Override
    public List<User> getMembers(String groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (!group.isPresent()) {
            return new ArrayList<>();
        }
        List<User> users = userRepository.findAll();
        List<User> members = new ArrayList<>();
        for (GroupMember member : group.get().getMembers()) {
            for (User user : users) {
                if (member.getUserId().equals(user.getUserId())) {
                    members.add(user);
                    break;
                }
            }
        }
        return members;
    }

    @Override
    public boolean addPost(String groupId, String authorId, String content, String imagePath) {
        List<Group> groups = new ArrayList<>(groupRepository.findAll());
        for (Group group : groups) {
            if (groupId.equals(group.getGroupId())) {
                String postId = groupId + "-" + (group.getPosts().size() + 1);
                group.getPosts().add(new Posts(content, authorId, postId, imagePath, LocalDateTime.now()));
                groupRepository.saveAll(groups);
                return true;
            }
        }
        return false;
    }
}
