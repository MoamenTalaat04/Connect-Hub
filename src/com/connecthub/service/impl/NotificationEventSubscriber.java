package com.connecthub.service.impl;

import com.connecthub.events.DomainEvent;
import com.connecthub.events.EventSubscriber;
import com.connecthub.service.NotificationService;

public class NotificationEventSubscriber implements EventSubscriber {
    private final NotificationService notificationService;

    public NotificationEventSubscriber(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void onEvent(DomainEvent event) {
        if ("chat.message.sent".equals(event.getType())) {
            String receiverId = event.getPayload().get("receiverId");
            String message = event.getPayload().get("message");
            notificationService.create(receiverId, "MESSAGE", "New message: " + message);
            return;
        }
        if ("friend.request.sent".equals(event.getType())) {
            String receiverId = event.getPayload().get("receiverId");
            notificationService.create(receiverId, "FRIEND_REQUEST", "You received a friend request.");
            return;
        }
        if ("group.joined".equals(event.getType())) {
            notificationService.create(event.getActorId(), "GROUP_ACTIVITY", "You joined a new group.");
            return;
        }
        if ("post.created".equals(event.getType())) {
            notificationService.create(event.getActorId(), "POST_ACTIVITY", "Your post is now live.");
        }
    }
}
