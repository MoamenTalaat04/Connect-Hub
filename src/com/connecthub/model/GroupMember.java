package com.connecthub.model;

import java.time.LocalDateTime;

public class GroupMember {
    private String userId;
    private String role;
    private LocalDateTime joinedAt;

    public GroupMember() {
    }

    public GroupMember(String userId, String role, LocalDateTime joinedAt) {
        this.userId = userId;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
}
