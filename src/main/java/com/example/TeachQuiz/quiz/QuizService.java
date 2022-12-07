package com.example.TeachQuiz.quiz;


import java.util.List;

public interface QuizService {
    Quiz addQuiz(Quiz quiz);
    Quiz updateQuiz(Quiz quiz);
    void deleteQuiz(Long id);

    List<QuizDTO> getQuiz();

    QuizDTO getQuizByName(String title);

    QuizDTO getQuizById(Long id);
}
