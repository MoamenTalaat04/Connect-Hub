package com.connecthub.repository.json;

import com.connecthub.model.Group;
import com.connecthub.repository.GroupRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

public class JsonGroupRepository implements GroupRepository {
    private final String groupsFile;
    private final ObjectMapper mapper;

    public JsonGroupRepository(String groupsFile) {
        this.groupsFile = groupsFile;
        this.mapper = JsonStoreSupport.createMapper();
    }

    @Override
    public List<Group> findAll() {
        return JsonStoreSupport.readList(mapper, groupsFile, Group[].class);
    }

    @Override
    public Optional<Group> findById(String groupId) {
        return findAll().stream().filter(g -> groupId.equals(g.getGroupId())).findFirst();
    }

    @Override
    public void saveAll(List<Group> groups) {
        JsonStoreSupport.writeList(mapper, groupsFile, groups);
    }
}
