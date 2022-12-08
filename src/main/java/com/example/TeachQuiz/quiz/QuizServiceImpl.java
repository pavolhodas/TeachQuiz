package com.example.TeachQuiz.quiz;

import com.example.TeachQuiz.question.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService{

    private final QuizRepository repository;
    private final ModelMapper mapper;
    private final QuestionService questionService;

    public QuizServiceImpl(QuizRepository repository, QuestionService questionService, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.questionService = questionService;
    }

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return repository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return this.repository.save(quiz);
    }

    @Override
    public QuizDTO getQuizById(String id) {
        return convertToDto(repository.getQuizByName(id));
    }

    @Override
    public void deleteQuiz(String id) {
        this.repository.getQuizByName(id);
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
        return quizDto;
    }
}
