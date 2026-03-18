package com.connecthub.events;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

public class DomainEvent {
    private final String type;
    private final String actorId;
    private final LocalDateTime occurredAt;
    private final Map<String, String> payload;

    public DomainEvent(String type, String actorId, Map<String, String> payload) {
        this.type = type;
        this.actorId = actorId;
        this.payload = payload == null ? Collections.emptyMap() : payload;
        this.occurredAt = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public String getActorId() {
        return actorId;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public Map<String, String> getPayload() {
        return payload;
    }
}
