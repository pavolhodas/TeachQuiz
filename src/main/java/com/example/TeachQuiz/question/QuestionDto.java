package com.example.TeachQuiz.question;

import com.example.TeachQuiz.answer.AnswerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class QuestionDto {

    private long id;
    private String questionContent;
    private List<AnswerDto> answerList;

    public QuestionDto(String content) {
        this.questionContent = content;
    }
}

