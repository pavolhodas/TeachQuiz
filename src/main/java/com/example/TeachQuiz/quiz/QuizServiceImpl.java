package com.example.TeachQuiz.quiz;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService{

    private final QuizRepository repository;

    private final ModelMapper mapper;

    public QuizServiceImpl(QuizRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
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
    public Quiz getQuizById(Long id) {
        return this.repository.getById(id);
    }

    @Override
    public void deleteQuiz(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public List<QuizDTO> getQuiz() {
        return null;
    }

    @Override
    public QuizDTO getQuizByName(String title) {
        return convertToDto(repository.getQuiz(title));
    }

    private QuizDTO convertToDto(Quiz quiz){
        QuizDTO quizDto = mapper.map(quiz, QuizDTO.class);
        quizDto.setTitle(quiz.getTitle());
        quizDto.setDescription(quiz.getDescription());
        return quizDto;
    }
}
