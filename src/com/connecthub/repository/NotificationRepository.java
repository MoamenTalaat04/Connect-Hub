package com.connecthub.repository;

import com.connecthub.model.Notification;

import java.util.List;

public interface NotificationRepository {
    List<Notification> findAll();

    List<Notification> findByUserId(String userId);

    void saveAll(List<Notification> notifications);
}
