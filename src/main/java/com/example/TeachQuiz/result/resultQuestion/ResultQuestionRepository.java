package com.example.TeachQuiz.result.resultQuestion;

import com.example.TeachQuiz.result.resultQuiz.ResultQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultQuestionRepository extends JpaRepository<ResultQuestion, Long> {
//    @Query("SELECT r FROM ResultQuestion r WHERE r.questionContent. = :quizId")
//    ResultQuestion findByResultQuestion(String resultQuestion);
}
