package com.example.TeachQuiz.result.resultQuiz;

public interface ResultQuizService {
    ResultQuiz saveResultquiz(ResultQuiz resultQuiz);

    ResultQuiz sendChosenAnswer(ResultQuiz resultQuiz);
}
