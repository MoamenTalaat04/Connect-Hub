package com.connecthub.repository;

import com.connecthub.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    List<Group> findAll();

    Optional<Group> findById(String groupId);

    void saveAll(List<Group> groups);
}
