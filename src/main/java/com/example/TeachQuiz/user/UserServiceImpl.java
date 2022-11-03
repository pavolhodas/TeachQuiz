package com.example.TeachQuiz.user;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User addUser( User user) {
        return this.repository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    @Override
    public User updateUser(User user) {
        return this.repository.save(user);
    }

}