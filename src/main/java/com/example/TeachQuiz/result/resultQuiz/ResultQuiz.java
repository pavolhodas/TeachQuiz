package com.example.TeachQuiz.result.resultQuiz;

import com.example.TeachQuiz.question.Question;
import com.example.TeachQuiz.result.resultQuestion.ResultQuestion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class ResultQuiz {

    @Id
    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "quiz_name")
    private List<ResultQuestion> questionList = new ArrayList<>();

}
