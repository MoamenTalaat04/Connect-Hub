package com.connecthub.service;

import com.connecthub.model.Conversation;
import com.connecthub.model.Message;

import java.util.List;

public interface ChatService {
    Conversation getOrCreateConversation(String userAId, String userBId);

    Message sendMessage(String senderId, String receiverId, String body);

    List<Message> getChatHistory(String userAId, String userBId);
}
