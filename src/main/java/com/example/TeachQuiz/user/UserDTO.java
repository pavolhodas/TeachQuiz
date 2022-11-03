package com.example.TeachQuiz.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserDTO {

    private long id;
    private String username;
    private String password;
    private String email;
    private String role;

}
