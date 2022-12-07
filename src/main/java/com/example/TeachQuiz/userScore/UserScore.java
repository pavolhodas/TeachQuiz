package com.example.TeachQuiz.userScore;

import com.example.TeachQuiz.quiz.Quiz;
import com.example.TeachQuiz.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class UserScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int score;
    private String teacherName;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Quiz quiz;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;
}
