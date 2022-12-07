package com.example.TeachQuiz.userScore;

import java.util.List;

public interface UserScoreService {
    UserScoreDto getScoreForCurrentUser(Long quizId);

    void saveScore(String quizName, Integer score);
}
