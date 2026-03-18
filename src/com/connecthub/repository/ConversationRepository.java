package com.connecthub.repository;

import com.connecthub.model.Conversation;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository {
    List<Conversation> findAll();

    Optional<Conversation> findById(String conversationId);

    Optional<Conversation> findByUsers(String userAId, String userBId);

    void saveAll(List<Conversation> conversations);
}
