package com.connecthub.events;

public interface EventSubscriber {
    void onEvent(DomainEvent event);
}
