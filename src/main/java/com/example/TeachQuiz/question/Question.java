package com.example.TeachQuiz.question;

import com.example.TeachQuiz.answer.Answer;
import com.example.TeachQuiz.quiz.Quiz;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    private Quiz quiz;

    @OneToMany(cascade = CascadeType.ALL,
                orphanRemoval = true)
    @JoinColumn(name = "question_id")
    List<Answer> answerList = new ArrayList<>();
}
