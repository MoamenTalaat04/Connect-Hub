package com.connecthub.repository;

import com.connecthub.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    void saveAll(List<User> users);

    void save(User user);

    Optional<User> findById(String userId);

    Optional<User> findByEmail(String email);

    Map<String, String> emailPasswordMap();
}
