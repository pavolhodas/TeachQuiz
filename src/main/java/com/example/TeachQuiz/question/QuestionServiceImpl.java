package com.example.TeachQuiz.question;

import com.example.TeachQuiz.quiz.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
    }

    @Override
    public List<Question> adminSaveQuestion(List<Question> questionList) {
        return questionRepository.saveAll(questionList);
    }

    @Override
    public void adminDelete(Long id) {
        questionRepository.deleteById(id);
    }
}

