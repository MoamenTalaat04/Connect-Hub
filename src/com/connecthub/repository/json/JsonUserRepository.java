package com.connecthub.repository.json;

import com.connecthub.model.User;
import com.connecthub.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonUserRepository implements UserRepository {
    private final String usersFile;
    private final ObjectMapper mapper;

    public JsonUserRepository(String usersFile) {
        this.usersFile = usersFile;
        this.mapper = JsonStoreSupport.createMapper();
    }

    @Override
    public List<User> findAll() {
        return JsonStoreSupport.readList(mapper, usersFile, User[].class);
    }

    @Override
    public void saveAll(List<User> users) {
        JsonStoreSupport.writeList(mapper, usersFile, users);
    }

    @Override
    public void save(User user) {
        List<User> users = new ArrayList<>(findAll());
        users.add(user);
        saveAll(users);
    }

    @Override
    public Optional<User> findById(String userId) {
        return findAll().stream().filter(u -> userId.equals(u.getUserId())).findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findAll().stream().filter(u -> email.equals(u.getEmail())).findFirst();
    }

    @Override
    public Map<String, String> emailPasswordMap() {
        return findAll().stream().collect(Collectors.toMap(User::getEmail, User::getHashedPassword));
    }
}
