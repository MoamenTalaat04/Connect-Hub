package com.connecthub.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleEventBus implements EventBus {
    private final Map<String, List<EventSubscriber>> subscribers = new HashMap<>();

    @Override
    public void subscribe(String eventType, EventSubscriber subscriber) {
        subscribers.computeIfAbsent(eventType, key -> new ArrayList<>()).add(subscriber);
    }

    @Override
    public void publish(DomainEvent event) {
        List<EventSubscriber> listeners = subscribers.get(event.getType());
        if (listeners == null) {
            return;
        }
        for (EventSubscriber listener : listeners) {
            listener.onEvent(event);
        }
    }
}
