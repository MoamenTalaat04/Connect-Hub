package com.connecthub.service;

import com.connecthub.model.User;

import java.util.Optional;

public interface AccountService {
    boolean signUp(String email, String userName, String password, String dateOfBirth, String bio, String coverPhotoPath, String profilePhotoPath);

    boolean login(String email, String password);

    Optional<User> findByEmail(String email);
}
