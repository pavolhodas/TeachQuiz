package com.example.TeachQuiz.result.resultQuiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResultQuizRepository extends JpaRepository<ResultQuiz, Long> {

//  @Query("SELECT r FROM ResultQuiz r WHERE r.name = :quizId")
//  ResultQuiz getQuizByName(@Param("name") String name);
}

