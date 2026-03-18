package com.connecthub.repository.json;

import com.connecthub.model.Notification;
import com.connecthub.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonNotificationRepository implements NotificationRepository {
    private final String notificationsFile;
    private final ObjectMapper mapper;

    public JsonNotificationRepository(String notificationsFile) {
        this.notificationsFile = notificationsFile;
        this.mapper = JsonStoreSupport.createMapper();
    }

    @Override
    public List<Notification> findAll() {
        return JsonStoreSupport.readList(mapper, notificationsFile, Notification[].class);
    }

    @Override
    public List<Notification> findByUserId(String userId) {
        List<Notification> notifications = new ArrayList<>();
        for (Notification notification : findAll()) {
            if (userId.equals(notification.getUserId())) {
                notifications.add(notification);
            }
        }
        return notifications;
    }

    @Override
    public void saveAll(List<Notification> notifications) {
        JsonStoreSupport.writeList(mapper, notificationsFile, notifications);
    }
}
