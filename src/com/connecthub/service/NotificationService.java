package com.connecthub.service;

import com.connecthub.model.Notification;

import java.util.List;

public interface NotificationService {
    Notification create(String userId, String type, String text);

    List<Notification> forUser(String userId);

    void markAllRead(String userId);
}
