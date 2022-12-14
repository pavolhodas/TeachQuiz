package com.example.TeachQuiz.quiz;

import com.example.TeachQuiz.question.QuestionService;
import com.example.TeachQuiz.user.User;
import com.example.TeachQuiz.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService{

    private final QuizRepository repository;
    private final ModelMapper mapper;
    private final QuestionService questionService;
    private final UserService userService;

    @Autowired
    public QuizServiceImpl(QuizRepository repository, ModelMapper mapper, QuestionService questionService, UserService userService) {
        this.repository = repository;
        this.mapper = mapper;
        this.questionService = questionService;
        this.userService = userService;
    }

    @Override
    public Quiz addQuiz(Quiz quiz) {
        quiz.setCreatorName(getCurrentUser().getUsername());
        return repository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return this.repository.save(quiz);
    }

    @Override
    public List<QuizDTO> getQuizByTeacherId() {
        String creatorName = getCurrentUser().getUsername();
        return repository.getQuizzesByCreator(creatorName).stream().map(this::convertToDto).toList();
    }

    @Override
    public void deleteQuiz(String name) {
        Quiz quiz =  this.repository.getQuizByName(name);

        this.repository.delete(quiz);
    }

    @Override
    public List<QuizDTO> getQuiz() {
        return null;
    }

    @Override
    public QuizDTO getQuizByName(String name) {
        return convertToDto(repository.getQuizByName(name));
    }

    private QuizDTO convertToDto(Quiz quiz){
        QuizDTO quizDto = mapper.map(quiz, QuizDTO.class);
        quizDto.setName(quiz.getName());
        quizDto.setDescription(quiz.getDescription());
        quizDto.setQuestionList(questionService.getQuestionsForQuiz(quiz.getName()));
        quizDto.setCreatorName(quiz.getCreatorName());
        return quizDto;
    }

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String username = userDetails.getUsername();
        return this.userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
