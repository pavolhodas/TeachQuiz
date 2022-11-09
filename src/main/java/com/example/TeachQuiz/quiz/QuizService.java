package com.example.TeachQuiz.quiz;


public interface QuizService {
    Quiz addQuiz(Quiz quiz);
    Quiz updateQuiz(Quiz quiz);
    Quiz getQuizById(Long id);
    void deleteQuiz(Long id);
}
