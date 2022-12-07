package com.example.TeachQuiz.answer;

import java.util.List;

public interface AnswerService {

    List<AnswerDto> getAnswers(Long id);
    void saveAnswer(Answer answer);
    void deleteAnswer(Long id);
    void saveAnswerList(List<Answer> answerList);
}
