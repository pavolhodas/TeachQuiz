package com.example.TeachQuiz.userScore;

import com.example.TeachQuiz.quiz.Quiz;
import com.example.TeachQuiz.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserScoreDto {

    private int score;
    //private Quiz quiz;
    //private User student;
    private String teacherName;
    private int repeated;

}
