package com.example.TeachQuiz.quiz;

import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService{

    private final QuizRepository repository;

    public QuizServiceImpl(QuizRepository repository) {
        this.repository = repository;
    }

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return this.repository.save(quiz);
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
}
