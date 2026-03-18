package com.connecthub.events;

public interface EventBus {
    void subscribe(String eventType, EventSubscriber subscriber);

    void publish(DomainEvent event);
}
