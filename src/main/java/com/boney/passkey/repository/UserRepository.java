package com.boney.passkey.repository;

import com.boney.passkey.model.User;

import java.util.HashMap;

public class UserRepository {
    private final HashMap<String, User> users = new HashMap<>();

    public void saveUser(User user) {
        users.put(user.getUserId(), user);
    }

    public User getUserById(String userId) {
        return users.get(userId);
    }
}
