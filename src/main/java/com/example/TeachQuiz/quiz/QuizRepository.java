package com.example.TeachQuiz.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q WHERE q.name = :name")
    Quiz getQuizByName(@Param("name") String name);

    @Query("SELECT q FROM Quiz q WHERE q.creator.id = :userId")
    List<Quiz> getQuizzesByCreator(@Param("userId") Long userId);

}
