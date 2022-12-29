package com.example.TeachQuiz.userScore;

import com.example.TeachQuiz.quiz.Quiz;
import com.example.TeachQuiz.quiz.QuizDTO;
import com.example.TeachQuiz.user.User;

import java.util.List;

public interface UserScoreService {
    UserScoreDto getScoreForCurrentUser(String quizName);

    void saveScore(String quizName, Integer score);

    List<User> getStudentsForTeacher(String quizName);

    List<QuizDTO> getAllQuizzesCreatedByTeacher();

    List<UserScoreDto> getUserScoreOfUserAndQuiz(String quizName, Long userId);
}
