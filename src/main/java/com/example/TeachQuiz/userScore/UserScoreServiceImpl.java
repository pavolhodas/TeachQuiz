package com.example.TeachQuiz.userScore;

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

    @Autowired
    public UserScoreServiceImpl(QuizRepository quizRepository, ModelMapper modelMapper, UserScoreRepository userScoreRepository, UserService userService) {
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
        this.userScoreRepository = userScoreRepository;
        this.userService = userService;
    }
    @Override
    public UserScoreDto getScoreForCurrentUser(Long quizId) {
        return convertToDto(userScoreRepository.getScoreForUserByQuiz(quizId, getCurrentUser().getId()));
    }

    @Override
    public void saveScore(String quizName, Integer score) {
        UserScore userScore = new UserScore();
        userScore.setUser(getCurrentUser());
        userScore.setQuiz(quizRepository.getQuiz(quizName));
        userScore.setScore(score);
        userScoreRepository.save(userScore);
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
        userScoreDto.setUserName(userScore.getUser().getUsername());
        userScoreDto.setScore(userScore.getScore());
        userScoreDto.setQuizName(userScore.getQuiz().getTitle());
        return userScoreDto;
    }
}
