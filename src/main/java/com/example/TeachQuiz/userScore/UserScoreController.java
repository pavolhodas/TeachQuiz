package com.example.TeachQuiz.userScore;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin("http://localhost:4200")
public class UserScoreController {

    private final UserScoreService userScoreService;

    public UserScoreController(UserScoreService userScoreService) {
        this.userScoreService = userScoreService;
    }

    @PostMapping(value = "/save/score/{quizName}/{score}")
    public void addScore(@PathVariable String quizName, @PathVariable Integer score){
        userScoreService.saveScore(quizName, score);
    }

    //getne score podla quizId
    @GetMapping("/score/{quizId}")
    public UserScoreDto getScoreForUser(@PathVariable Long quizId){
        return userScoreService.getScoreForCurrentUser(quizId);
    }

}
