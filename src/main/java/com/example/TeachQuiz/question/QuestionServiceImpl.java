package com.example.TeachQuiz.question;

import com.example.TeachQuiz.answer.AnswerService;
import com.example.TeachQuiz.user.User;
import com.example.TeachQuiz.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    private final ModelMapper modelMapper;

    private final AnswerService answerService;

    private final UserService userService;

    public QuestionServiceImpl(QuestionRepository questionRepository, ModelMapper modelMapper, AnswerService answerService, UserService userService) {
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
        this.answerService = answerService;
        this.userService = userService;
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

    @Override
    public List<QuestionDto> getQuestionsForQuiz(String quizId) {

        List<QuestionDto> questionDtos = new java.util.ArrayList<>(questionRepository.findAllByQuizId(quizId).stream().map(question -> {
            QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
            questionDto.setAnswerList(answerService.getAnswers(question.getId()));
            return questionDto;
        }).toList());
        Collections.shuffle(questionDtos);
        return questionDtos;
    }

    private QuestionDto convertToDto(Question question){
        QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
        questionDto.setContent(question.getContent());
        questionDto.setId(question.getId());
        questionDto.setAnswerList(answerService.getAnswers(question.getId()));
        return questionDto;
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

