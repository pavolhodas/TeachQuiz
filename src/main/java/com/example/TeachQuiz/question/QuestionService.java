package com.example.TeachQuiz.question;

import java.util.List;

public interface QuestionService {
    Question getQuestionById(Long id);
    List<Question> adminSaveQuestion(List<Question> questionList);
    void adminDelete(Long id);
    List<QuestionDto> getQuestionsForQuiz(String quizId);
}

