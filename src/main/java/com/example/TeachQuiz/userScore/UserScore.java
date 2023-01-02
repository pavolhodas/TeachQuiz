package com.example.TeachQuiz.userScore;

import com.example.TeachQuiz.quiz.Quiz;
import com.example.TeachQuiz.result.resultQuiz.ResultQuiz;
import com.example.TeachQuiz.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private int repeated;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User student;

    @ManyToOne
    private ResultQuiz resultQuiz;
}
