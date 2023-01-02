package com.example.TeachQuiz.result.resultQuiz;

import com.example.TeachQuiz.quiz.QuizRepository;
import com.example.TeachQuiz.result.resultAnswer.ResultAnswer;
import com.example.TeachQuiz.result.resultQuestion.ResultQuestion;
import com.example.TeachQuiz.result.resultQuestion.ResultQuestionRepository;
import com.example.TeachQuiz.user.User;
import com.example.TeachQuiz.user.UserService;
import com.example.TeachQuiz.userScore.UserScore;
import com.example.TeachQuiz.userScore.UserScoreRepository;
import com.example.TeachQuiz.userScore.UserScoreService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultQuizServiceImpl implements ResultQuizService {

  ResultQuizRepository resultQuizRepository;
  ResultQuestionRepository resultQuestionRepository;
  UserService userService;
  UserScoreRepository userScoreRepository;
  UserScoreService userScoreService;
  QuizRepository quizRepository;

  public ResultQuizServiceImpl(ResultQuizRepository resultQuizRepository, ResultQuestionRepository resultQuestionRepository, UserService userService, UserScoreRepository userScoreRepository, UserScoreService userScoreService, QuizRepository quizRepository) {
    this.resultQuizRepository = resultQuizRepository;
    this.resultQuestionRepository = resultQuestionRepository;
    this.userService = userService;
    this.userScoreRepository = userScoreRepository;
    this.userScoreService = userScoreService;
    this.quizRepository = quizRepository;
  }

  @Override
  public ResultQuiz saveResultquiz(ResultQuiz resultQuiz) {
    return resultQuizRepository.save(resultQuiz);
  }

  @Override
  public ResultQuiz sendChosenAnswer(ResultQuestion resultQuestion, String resultQuizName) {
    UserScore userScore = new UserScore();
    userScore.setStudent(getCurrentUser());
    userScore.setResultQuiz(resultQuizRepository.getResultQuizByName(resultQuizName));
    userScore.setTeacherName(quizRepository.getQuizByName(resultQuizName).getCreator().getUsername());
    List<UserScore> userScores = userScoreRepository.getScoreForUserByQuizAndTeacher(resultQuizName, getCurrentUser().getId(), quizRepository.getQuizByName(resultQuizName).getCreator().getUsername());
    userScore.setRepeated(userScores.size());

    ResultQuiz resultQuiz = resultQuizRepository.getResultQuizByName(resultQuizName);
    resultQuiz.getQuestionList().add(resultQuestion);
    resultQuizRepository.save(resultQuiz);
    userScoreRepository.save(userScore);
    //userScoreService.saveScore(resultQuiz.getName(), 40);
    return resultQuiz;
  }


  private User getCurrentUser() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal();

    return this.userService.getUserByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
