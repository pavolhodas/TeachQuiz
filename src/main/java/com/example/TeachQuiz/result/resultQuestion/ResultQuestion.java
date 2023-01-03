package com.example.TeachQuiz.result.resultQuestion;

import com.example.TeachQuiz.answer.Answer;
import com.example.TeachQuiz.quiz.Quiz;
import com.example.TeachQuiz.result.resultAnswer.ResultAnswer;
import com.example.TeachQuiz.result.resultQuiz.ResultQuiz;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
public class ResultQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionContent;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "question_id")
    List<ResultAnswer> answerList;
}
