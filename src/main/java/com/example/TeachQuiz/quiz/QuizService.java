package com.example.TeachQuiz.quiz;


import java.util.List;

public interface QuizService {
    Quiz addQuiz(Quiz quiz);
    Quiz updateQuiz(Quiz quiz);
    Quiz getQuizById(Long id);
    void deleteQuiz(Long id);

    List<QuizDTO> getQuiz();

    QuizDTO getQuizByName(String title);
}
