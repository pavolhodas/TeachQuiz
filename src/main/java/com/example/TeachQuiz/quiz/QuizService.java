package com.example.TeachQuiz.quiz;


import java.util.List;

public interface QuizService {
    Quiz addQuiz(Quiz quiz);
    Quiz updateQuiz(Quiz quiz);
    void deleteQuiz(String id);

    List<QuizDTO> getQuiz();

    QuizDTO getQuizByName(String name);

    //QuizDTO getQuizByName(String id);

    List<QuizDTO> getQuizByTeacherId();
}
