package com.example.TeachQuiz.userScore;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserScoreRepository extends CrudRepository<UserScore, Long> {

    @Query("SELECT u FROM UserScore u WHERE u.quiz.id = :quizId and u.user.id = :id")
    UserScore getScoreForUserByQuiz(@Param("quizId") Long quizId, @Param("id") Long id);

}
