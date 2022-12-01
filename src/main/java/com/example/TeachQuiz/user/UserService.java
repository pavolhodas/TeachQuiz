package com.example.TeachQuiz.user;

import java.util.Optional;

public interface UserService {
    UserDTO addUser(User user);
    Optional<User> getUserByUsername(String username);
    User updateUser(User user);

    boolean verifyUser(String code);

    String getAccessToken(String username);
    void changePassword(String username, String password);

    void sendPasswordResetEmail(String email);
}