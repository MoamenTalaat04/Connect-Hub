package com.connecthub.model;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private String conversationId;
    private String userAId;
    private String userBId;
    private List<Message> messages;

    public Conversation() {
    }

    public Conversation(String conversationId, String userAId, String userBId) {
        this.conversationId = conversationId;
        this.userAId = userAId;
        this.userBId = userBId;
        this.messages = new ArrayList<>();
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getUserAId() {
        return userAId;
    }

    public String getUserBId() {
        return userBId;
    }

    public List<Message> getMessages() {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        return messages;
    }
}
