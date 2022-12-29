package com.example.TeachQuiz.userScore;

import com.example.TeachQuiz.question.QuestionService;
import com.example.TeachQuiz.quiz.Quiz;
import com.example.TeachQuiz.quiz.QuizDTO;
import com.example.TeachQuiz.quiz.QuizRepository;
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
public class UserScoreServiceImpl implements UserScoreService {

    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;
    private final UserScoreRepository userScoreRepository;
    private final UserService userService;
    private final QuestionService questionService;

    @Autowired
    public UserScoreServiceImpl(QuizRepository quizRepository, ModelMapper modelMapper, UserScoreRepository userScoreRepository, UserService userService, QuestionService questionService) {
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
        this.userScoreRepository = userScoreRepository;
        this.userService = userService;
        this.questionService = questionService;
    }

    @Override
    public UserScoreDto getScoreForCurrentUser(String quizName) {
        return convertToDto(userScoreRepository.getScoreForUserByQuiz(quizName, getCurrentUser().getId()));
    }

    @Override
    public void saveScore(String quizName, Integer score) {
        UserScore userScore = new UserScore();
        userScore.setStudent(getCurrentUser());
        userScore.setQuiz(quizRepository.getQuizByName(quizName));
        userScore.setScore(score);
        userScore.setTeacherName(quizRepository.getQuizByName(quizName).getCreator().getUsername());
        List<UserScore> userScores = userScoreRepository.getScoreForUserByQuizAndTeacher(quizName, getCurrentUser().getId(), quizRepository.getQuizByName(quizName).getCreator().getUsername());
        userScore.setRepeated(userScores.size());
        userScoreRepository.save(userScore);
    }

    @Override
    public List<User> getStudentsForTeacher(String quizName) {
        User user = getCurrentUser();
        return userScoreRepository.getScoreForTeacherByStudents(user.getUsername(), quizName);
    }

    @Override
    public List<QuizDTO> getAllQuizzesCreatedByTeacher() {
        return userScoreRepository.getAllTeacherQuizzes(getCurrentUser().getUsername()).stream().map(this::convertQuizToDto).toList();
    }

    @Override
    public List<UserScoreDto> getUserScoreOfUserAndQuiz(String quizName, Long userId) {
        return userScoreRepository.getUserScoreByQuizAndUser(quizName, userId).stream().map(this::convertToDto).toList();
    }

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String username = userDetails.getUsername();
        return this.userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    private UserScoreDto convertToDto(UserScore userScore){
        UserScoreDto userScoreDto = modelMapper.map(userScore, UserScoreDto.class);
        userScoreDto.setTeacherName(userScore.getTeacherName());
        //userScoreDto.setStudent(userScore.getStudent());
        userScoreDto.setScore(userScore.getScore());
        //userScoreDto.setQuiz(userScore.getQuiz());
        return userScoreDto;
    }

    private QuizDTO convertQuizToDto(Quiz quiz){
        QuizDTO quizDto = modelMapper.map(quiz, QuizDTO.class);
        quizDto.setName(quiz.getName());
        quizDto.setDescription(quiz.getDescription());
        quizDto.setQuestionList(questionService.getQuestionsForQuiz(quiz.getName()));
        quizDto.setCreator(quiz.getCreator());
        return quizDto;
    }

}
