package com.example.TeachQuiz.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
@CrossOrigin("http://localhost:4200")
public class UserController {

private final UserService userService;

        @Autowired // add here
        public UserController(UserService userService) {
        this.userService = userService;
        }

        @PostMapping("/register")
        public void registerUser(@RequestBody User user){
        userService.addUser(user);
        }

        @GetMapping("/verify/{code}")
        public String verifyUser(@PathVariable String code) {
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

        @PutMapping("/changePassword/{email}/{password}")
        public void changePassword(@PathVariable String email, @PathVariable String password) {
                userService.changePassword(email, password);
        }

        @GetMapping("/disabled/user")
        public User getDisabledUser() {
                return userService.getDisabledUser();
        }

        @GetMapping("/role")
        public String getRole() {
    return userService.getRole();
  }
}
