package com.example.TeachQuiz.result.resultAnswer;

import com.example.TeachQuiz.question.Question;
import com.example.TeachQuiz.result.resultQuestion.ResultQuestion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
public class ResultAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answerContent;
    private boolean isCorrect;

    @ManyToOne
    private ResultQuestion question;
}
