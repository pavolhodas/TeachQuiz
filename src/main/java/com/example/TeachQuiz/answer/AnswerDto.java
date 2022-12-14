package com.example.TeachQuiz.answer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AnswerDto {

    private long id;
    private String content;
    private boolean isCorrect;
}
