package com.example.TeachQuiz.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URL;

@RestController
@RequestMapping
@CrossOrigin("http://localhost:4200")
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

        @PostMapping("/register")
        public void registerUser(@RequestBody User user){
        userService.addUser(user);
        }

        @GetMapping("/verify/{username}")
        public String verifyUser(@PathVariable String username, @Param("code") String code) {
                userService.getAccessToken(username);
                if (userService.verifyUser(code)) {
                        return "verify_success";
                } else {
                        return "verify_fail";
                }
        }

        @GetMapping("/changePasswordEmail/{email}")
        public void sendPasswordResetEmail(@PathVariable String email) {
                userService.sendPasswordResetEmail(email);
        }

        @PutMapping("/changePassword/{username}/{password}")
        public void changePassword(@PathVariable String username, @PathVariable String password) {
                userService.changePassword(username, password);
        }
}
