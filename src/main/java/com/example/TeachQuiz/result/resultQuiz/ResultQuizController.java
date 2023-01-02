package com.example.TeachQuiz.result.resultQuiz;

import com.example.TeachQuiz.result.resultQuestion.ResultQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin("http://localhost:4200")
public class ResultQuizController {
  ResultQuizService resultQuizService;

  @Autowired
  public ResultQuizController(ResultQuizService resultQuizService){
    this.resultQuizService = resultQuizService;
  }

  @PostMapping("/add/resultQuiz")
  @ResponseBody
  public ResultQuiz addResultQuiz(@RequestBody ResultQuiz resultQuiz){
    return resultQuizService.saveResultquiz(resultQuiz);
  }

  @PutMapping("/update/resultQuiz/{resultQuizName}")
  @ResponseBody
  public ResultQuiz sendChosenAnswer(@RequestBody ResultQuestion resultQuestion, @PathVariable String resultQuizName){
    return resultQuizService.sendChosenAnswer(resultQuestion, resultQuizName);
  }
}
