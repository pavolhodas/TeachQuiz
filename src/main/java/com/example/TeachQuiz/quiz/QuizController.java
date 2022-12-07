package com.example.TeachQuiz.quiz;

import com.example.TeachQuiz.question.Question;
import com.example.TeachQuiz.question.QuestionDto;
import com.example.TeachQuiz.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuizController {

    private final QuizService quizService;
    private final QuestionService questionService;

    @Autowired
    public QuizController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    @PostMapping("/add/quiz")
    public Quiz addQuiz(@RequestBody Quiz quiz){
        return quizService.addQuiz(quiz);
    }

    @PostMapping("/update/quiz")
    public Quiz updateQuiz(Quiz quiz){
        return quizService.updateQuiz(quiz);
    }

    @DeleteMapping("/delete/quiz/{id}")
    public void deleteQuiz(@PathVariable Long id){
        quizService.deleteQuiz(id);
    }

    @GetMapping("/login")
    public Integer verifyUser() {
        return 1;
    }

    @GetMapping("/get/{title}")
    public QuizDTO getQuiz(@PathVariable String title){
        return quizService.getQuizByName(title);
    }

    @GetMapping("/quiz/{quizId}")
    public List<QuestionDto> getQuestionsForQuiz(@PathVariable Long quizId){
        return questionService.getQuestionsForQuiz(quizId);
    }

    @GetMapping("/get/quiz/{quizId}")
    public QuizDTO getQuizDto(@PathVariable Long quizId){
        return quizService.getQuizById(quizId);
    }
}
