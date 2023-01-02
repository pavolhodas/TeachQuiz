package com.example.TeachQuiz.result.resultQuiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultQuizRepository extends JpaRepository<ResultQuiz, Long> {

  @Query("SELECT r FROM ResultQuiz r WHERE r.quizName = :quizName and r.studentName = :studentName")
  ResultQuiz getResultQuizByName(@Param("quizName") String quizName, @Param("studentName") String studentName);
}

