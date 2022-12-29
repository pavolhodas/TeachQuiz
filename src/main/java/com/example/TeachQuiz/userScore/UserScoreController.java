package com.example.TeachQuiz.userScore;

import com.example.TeachQuiz.quiz.QuizDTO;
import com.example.TeachQuiz.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin("http://localhost:4200")
public class UserScoreController {

    private final UserScoreService userScoreService;

    @Autowired
    public UserScoreController(UserScoreService userScoreService) {
        this.userScoreService = userScoreService;
    }

    @PostMapping(value = "/save/score/{quizName}/{score}")
    public void addScore(@PathVariable String quizName, @PathVariable Integer score){
        userScoreService.saveScore(quizName, score);
    }

    //getne score podla quizId
    @GetMapping("/score/{quizName}")
    public UserScoreDto getScoreForUser(@PathVariable String quizName){
        return userScoreService.getScoreForCurrentUser(quizName);
    }

    //vsetky quizy ktore vytvoril teacher moze rozkliknut a zobrazia sa studenti
    @GetMapping("/all/quiz/teacher")
    public List<QuizDTO> getAllQuizzesCreatedByTeacher(){
        return userScoreService.getAllQuizzesCreatedByTeacher();
    }

    //po kliknuti na quiz zobrazi sa zoznam studentov
    @GetMapping("/users/{quizName}")
    public List<User> getStudentsOfTeacher(@PathVariable String quizName){
        return userScoreService.getStudentsForTeacher(quizName);
    }

    @GetMapping("/users/{quizName}/{userId}")
    @ResponseBody
    public List<UserScoreDto> getUserScoreOfUserAndQuiz(@PathVariable String quizName, @PathVariable Long userId){
        return userScoreService.getUserScoreOfUserAndQuiz(quizName, userId);
    }

}
