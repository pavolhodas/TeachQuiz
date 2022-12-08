package com.example.TeachQuiz.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);

    @Query("SELECT u FROM User u WHERE u.email = :email ")
    User getUserByEmail(@Param("email")String email);

    @Query("SELECT u FROM User u WHERE u.enabled = false")
    User getDisabledUser();
}