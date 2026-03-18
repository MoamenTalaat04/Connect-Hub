package com.connecthub.repository.json;

import com.connecthub.model.Conversation;
import com.connecthub.repository.ConversationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

public class JsonConversationRepository implements ConversationRepository {
    private final String conversationsFile;
    private final ObjectMapper mapper;

    public JsonConversationRepository(String conversationsFile) {
        this.conversationsFile = conversationsFile;
        this.mapper = JsonStoreSupport.createMapper();
    }

    @Override
    public List<Conversation> findAll() {
        return JsonStoreSupport.readList(mapper, conversationsFile, Conversation[].class);
    }

    @Override
    public Optional<Conversation> findById(String conversationId) {
        return findAll().stream().filter(c -> conversationId.equals(c.getConversationId())).findFirst();
    }

    @Override
    public Optional<Conversation> findByUsers(String userAId, String userBId) {
        return findAll().stream().filter(c ->
                (userAId.equals(c.getUserAId()) && userBId.equals(c.getUserBId())) ||
                        (userAId.equals(c.getUserBId()) && userBId.equals(c.getUserAId()))
        ).findFirst();
    }

    @Override
    public void saveAll(List<Conversation> conversations) {
        JsonStoreSupport.writeList(mapper, conversationsFile, conversations);
    }
}
