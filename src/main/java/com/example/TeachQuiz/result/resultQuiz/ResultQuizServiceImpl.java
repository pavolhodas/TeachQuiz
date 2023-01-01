package com.example.TeachQuiz.result.resultQuiz;

import com.example.TeachQuiz.result.resultQuestion.ResultQuestion;
import com.example.TeachQuiz.result.resultQuestion.ResultQuestionRepository;
import com.example.TeachQuiz.user.User;
import com.example.TeachQuiz.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ResultQuizServiceImpl implements ResultQuizService {

  ResultQuizRepository resultQuizRepository;
  ResultQuestionRepository resultQuestionRepository;
  UserService userService;

  public ResultQuizServiceImpl(ResultQuizRepository resultQuizRepository, ResultQuestionRepository resultQuestionRepository, UserService userService) {
    this.resultQuizRepository = resultQuizRepository;
    this.resultQuestionRepository = resultQuestionRepository;
    this.userService = userService;
  }

  @Override
  public ResultQuiz saveResultquiz(ResultQuiz resultQuiz) {
    return resultQuizRepository.save(resultQuiz);
  }

//  @Override
//  public ResultQuiz sendChosenAnswer(String resultQuiz, String resultQuestion, String resultAnswer) {
//    ResultQuiz resultQuiz01 = resultQuizRepository.getQuizByName(resultQuiz);
//    //ResultQuestion resultQuestion1 = resultQuestionRepository.findByResultQuestion(resultQuestion);
//    resultQuiz01.setQuestionList();
//    return resultQuiz;
//  }

  private User getCurrentUser() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal();

    return this.userService.getUserByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
