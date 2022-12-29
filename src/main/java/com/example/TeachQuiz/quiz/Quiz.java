package com.example.TeachQuiz.quiz;

import com.example.TeachQuiz.question.Question;
import com.example.TeachQuiz.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Quiz {

    @Id
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "quiz_name")
    private List<Question> questionList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private User creator;

}
