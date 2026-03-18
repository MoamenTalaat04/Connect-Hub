package com.connecthub.model;

import java.time.LocalDateTime;

public class Notification {
    private String notificationId;
    private String userId;
    private String type;
    private String text;
    private boolean read;
    private LocalDateTime createdAt;

    public Notification() {
    }

    public Notification(String notificationId, String userId, String type, String text, boolean read, LocalDateTime createdAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.type = type;
        this.text = text;
        this.read = read;
        this.createdAt = createdAt;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
