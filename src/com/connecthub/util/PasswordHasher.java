package com.connecthub.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordHasher {
    private PasswordHasher() {
    }

    public static String hash(String password) throws NoSuchAlgorithmException {
        MessageDigest encryptor = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = encryptor.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashedBytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
