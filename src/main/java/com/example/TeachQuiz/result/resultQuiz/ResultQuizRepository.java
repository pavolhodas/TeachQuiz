package com.example.TeachQuiz.result.resultQuiz;

import com.example.TeachQuiz.result.resultQuestion.ResultQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultQuizRepository extends JpaRepository<ResultQuiz, Long> {

  @Query("SELECT r FROM ResultQuiz r WHERE r.name = :quizId")
  ResultQuiz getQuizByName(@Param("name") String name);
}

