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
//        this.createAndPersistUser("marek", "marek123", "pavolhodas4@gmail.com");
//        this.createAndPersistUser("adko", "adko1234", "paolhodas4@gmail.com");
    }

    private void createAndPersistUser(String username, String password, String email) {
        String encodedPassword = this.passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, email);
        this.userService.addUser(user);
    }

}