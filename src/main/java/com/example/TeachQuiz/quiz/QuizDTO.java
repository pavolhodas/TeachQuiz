package com.example.TeachQuiz.quiz;

import com.example.TeachQuiz.question.QuestionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuizDTO {

    private String name;
    private String description;
    private List<QuestionDto> questionList;
    private String creatorName;
}
