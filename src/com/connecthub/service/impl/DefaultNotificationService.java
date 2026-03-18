package com.connecthub.service.impl;

import com.connecthub.model.Notification;
import com.connecthub.repository.NotificationRepository;
import com.connecthub.service.NotificationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DefaultNotificationService implements NotificationService {
    private final NotificationRepository notificationRepository;

    public DefaultNotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification create(String userId, String type, String text) {
        List<Notification> notifications = new ArrayList<>(notificationRepository.findAll());
        Notification notification = new Notification(
                String.valueOf(notifications.size() + 1),
                userId,
                type,
                text,
                false,
                LocalDateTime.now()
        );
        notifications.add(notification);
        notificationRepository.saveAll(notifications);
        return notification;
    }

    @Override
    public List<Notification> forUser(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public void markAllRead(String userId) {
        List<Notification> all = new ArrayList<>(notificationRepository.findAll());
        for (Notification notification : all) {
            if (userId.equals(notification.getUserId())) {
                notification.setRead(true);
            }
        }
        notificationRepository.saveAll(all);
    }
}
