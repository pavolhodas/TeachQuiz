package com.example.TeachQuiz.answer;

import com.example.TeachQuiz.question.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter @NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answerContent;
    private boolean isCorrect;

    @ManyToOne
    private Question question;
}
