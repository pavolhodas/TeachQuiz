package com.example.TeachQuiz;

import com.example.TeachQuiz.user.User;
import com.example.TeachQuiz.user.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultInitialization implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DefaultInitialization(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
//        this.createAndPersistUser("marek", "marek123");
//        this.createAndPersistUser("adko", "adko1234");
    }

    private void createAndPersistUser(String username, String password) {
        String encodedPassword = this.passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, 0);
        this.userService.addUser(user);
    }

}