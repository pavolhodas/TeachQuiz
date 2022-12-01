package com.example.TeachQuiz.user;

import java.util.Optional;

public interface UserService {
    UserDTO addUser(User user);
    Optional<User> getUserByUsername(String username);
    User updateUser(User user);

    boolean verifyUser(String code);

    String getAccessToken(String username);
    void changePassword(String email, String password);
    User getDisabledUser();
    void sendPasswordResetEmail(String email);
}