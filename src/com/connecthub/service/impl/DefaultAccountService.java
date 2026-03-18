package com.connecthub.service.impl;

import com.connecthub.model.User;
import com.connecthub.repository.UserRepository;
import com.connecthub.service.AccountService;
import com.connecthub.util.PasswordHasher;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

public class DefaultAccountService implements AccountService {
    private final UserRepository userRepository;

    public DefaultAccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean signUp(String email, String userName, String password, String dateOfBirth, String bio, String coverPhotoPath, String profilePhotoPath) {
        try {
            String hashedPassword = PasswordHasher.hash(password);
            int id = userRepository.findAll().size() + 1;
            User user = new User(String.valueOf(id), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    "Offline", dateOfBirth, userName, email, hashedPassword, coverPhotoPath, bio, profilePhotoPath);
            userRepository.save(user);
            return true;
        } catch (NoSuchAlgorithmException ex) {
            return false;
        }
    }

    @Override
    public boolean login(String email, String password) {
        try {
            String hashed = PasswordHasher.hash(password);
            String stored = userRepository.emailPasswordMap().get(email);
            return hashed.equals(stored);
        } catch (NoSuchAlgorithmException ex) {
            return false;
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
