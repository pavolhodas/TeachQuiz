package com.example.TeachQuiz.userScore;

import com.example.TeachQuiz.quiz.Quiz;
import com.example.TeachQuiz.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserScoreRepository extends CrudRepository<UserScore, Long> {

    @Query("SELECT u FROM UserScore u WHERE u.quiz.name = :quizName and u.student.id = :id")
    UserScore getScoreForUserByQuiz(@Param("quizName") String quizName, @Param("id") Long id);

    @Query("SELECT u.student FROM UserScore u WHERE u.teacherName = :teacherName and u.quiz.name = :quizName")
    List<User> getScoreForTeacherByStudents(@Param("teacherName") String teacherName, @Param("quizName") String quizName);

    @Query("SELECT u.quiz FROM UserScore u WHERE u.teacherName = :teacherName")
    List<Quiz> getAllTeacherQuizzes(@Param("teacherName") String teacherName);

    @Query("SELECT u FROM UserScore u WHERE u.quiz.name = :quizName and u.student.id = :studentId and u.teacherName = :teacherName")
    List<UserScore> getScoreForUserByQuizAndTeacher(@Param("quizName") String quizName, @Param("studentId") Long studentId, @Param("teacherName") String teacherName);

    @Query("SELECT u FROM UserScore u WHERE u.quiz.name = :quizName and u.student.id = :studentId")
    List<UserScore> getUserScoreByQuizAndUser(@Param("quizName") String quizName, @Param("studentId") Long studentId);

}
