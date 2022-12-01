package com.example.TeachQuiz.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role;
    @Column(unique = true)
    private String email;
    private boolean enabled;
    private String verificationCode;


    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
