package com.example.TeachQuiz.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URL;

@RestController
@RequestMapping
public class UserController {

private final UserService userService;



@Autowired // add here
public UserController(UserService userService) {
        this.userService = userService;
        }

@GetMapping("/user/check")
public void getUser() {
        System.out.println("User haha");
        }


@GetMapping("/admin/check")
public void getAdmin() {
        System.out.println("admin fu");
        }

        String userName = "";

@PostMapping("/register")
public void registerUser(@RequestBody User user){
        userService.addUser(user);
        }

}
