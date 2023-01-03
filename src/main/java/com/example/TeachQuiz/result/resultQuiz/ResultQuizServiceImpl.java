package com.example.TeachQuiz.result.resultQuiz;

import com.example.TeachQuiz.quiz.QuizRepository;
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
    resultQuiz.setStudentName(getCurrentUser().getUsername());
    ResultQuiz resultQuiz1 = resultQuizRepository.getResultQuizByName(resultQuiz.getQuizName(), getCurrentUser().getUsername());
    if(resultQuiz1 != null){
      System.out.println("student already took this quiz");
      return null;
    }
    return resultQuizRepository.save(resultQuiz);
  }

  @Override
  public ResultQuiz sendChosenAnswer(ResultQuestion resultQuestion, String resultQuizName) {
    UserScore userScore = new UserScore();
    userScore.setResultQuiz(resultQuizRepository.getResultQuizByName(resultQuizName, getCurrentUser().getUsername()));
    userScore.setTeacherName(quizRepository.getQuizByName(resultQuizName).getCreatorName());
    List<UserScore> userScores = userScoreRepository.getScoreForUserByQuizAndTeacher(resultQuizName, getCurrentUser().getId(), quizRepository.getQuizByName(resultQuizName).getCreatorName());
    userScore.setRepeated(userScores.size());
    userScore.setStudent(getCurrentUser());

    ResultQuiz resultQuiz = resultQuizRepository.getResultQuizByName(resultQuizName, getCurrentUser().getUsername());
    resultQuiz.getQuestionList().add(resultQuestion);
    resultQuizRepository.save(resultQuiz);
    userScoreRepository.save(userScore);
    //userScoreService.saveScore(resultQuiz.getName(), 40);
    return resultQuiz;
  }

  @Override
  public ResultQuiz getResultQuizByName(String resultQuizName) {
    //aby to returnlo cely result quiz s otazkami odpovedami sprav!!!!
    return resultQuizRepository.getResultQuizByName(resultQuizName, getCurrentUser().getUsername());
  }


  private User getCurrentUser() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal();

    return this.userService.getUserByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
