package com.connecthub.service.impl;

import com.connecthub.events.DomainEvent;
import com.connecthub.events.EventBus;
import com.connecthub.model.Conversation;
import com.connecthub.model.Message;
import com.connecthub.repository.ConversationRepository;
import com.connecthub.service.ChatService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultChatService implements ChatService {
    private final ConversationRepository conversationRepository;
    private final EventBus eventBus;

    public DefaultChatService(ConversationRepository conversationRepository, EventBus eventBus) {
        this.conversationRepository = conversationRepository;
        this.eventBus = eventBus;
    }

    @Override
    public Conversation getOrCreateConversation(String userAId, String userBId) {
        Conversation existing = conversationRepository.findByUsers(userAId, userBId).orElse(null);
        if (existing != null) {
            return existing;
        }
        List<Conversation> conversations = new ArrayList<>(conversationRepository.findAll());
        Conversation created = new Conversation(String.valueOf(conversations.size() + 1), userAId, userBId);
        conversations.add(created);
        conversationRepository.saveAll(conversations);
        return created;
    }

    @Override
    public Message sendMessage(String senderId, String receiverId, String body) {
        Conversation conversation = getOrCreateConversation(senderId, receiverId);
        List<Conversation> conversations = new ArrayList<>(conversationRepository.findAll());
        Message message = new Message(
                conversation.getConversationId() + "-" + (conversation.getMessages().size() + 1),
                conversation.getConversationId(),
                senderId,
                receiverId,
                body,
                LocalDateTime.now()
        );
        for (Conversation item : conversations) {
            if (conversation.getConversationId().equals(item.getConversationId())) {
                item.getMessages().add(message);
                break;
            }
        }
        conversationRepository.saveAll(conversations);
        HashMap<String, String> payload = new HashMap<>();
        payload.put("receiverId", receiverId);
        payload.put("message", body);
        eventBus.publish(new DomainEvent("chat.message.sent", senderId, payload));
        return message;
    }

    @Override
    public List<Message> getChatHistory(String userAId, String userBId) {
        Conversation conversation = conversationRepository.findByUsers(userAId, userBId).orElse(null);
        if (conversation == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(conversation.getMessages());
    }
}
