package com.example.TeachQuiz.result.resultQuiz;

import com.example.TeachQuiz.result.resultQuestion.ResultQuestion;
import com.example.TeachQuiz.userScore.UserScore;

public interface ResultQuizService {
    ResultQuiz saveResultquiz(ResultQuiz resultQuiz);
    ResultQuiz sendChosenAnswer(ResultQuestion resultQuestion, String resultQuizName);
}
