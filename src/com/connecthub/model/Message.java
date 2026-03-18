package com.connecthub.model;

import java.time.LocalDateTime;

public class Message {
    private String messageId;
    private String conversationId;
    private String senderId;
    private String receiverId;
    private String body;
    private LocalDateTime sentAt;

    public Message() {
    }

    public Message(String messageId, String conversationId, String senderId, String receiverId, String body, LocalDateTime sentAt) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.body = body;
        this.sentAt = sentAt;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}
