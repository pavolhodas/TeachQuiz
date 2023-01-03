package com.example.TeachQuiz.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q WHERE q.name = :name")
    Quiz getQuizByName(@Param("name") String name);

    @Query("SELECT q FROM Quiz q WHERE q.creatorName = :creatorName")
    List<Quiz> getQuizzesByCreator(@Param("creatorName") String creatorName);

}
