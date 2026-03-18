package com.connecthub.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupId;
    private String name;
    private String description;
    private String ownerId;
    private LocalDateTime createdAt;
    private List<GroupMember> members;
    private List<Posts> posts;

    public Group() {
    }

    public Group(String groupId, String name, String description, String ownerId, LocalDateTime createdAt) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.members = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public String getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<GroupMember> getMembers() {
        if (members == null) {
            members = new ArrayList<>();
        }
        return members;
    }

    public List<Posts> getPosts() {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        return posts;
    }
}
