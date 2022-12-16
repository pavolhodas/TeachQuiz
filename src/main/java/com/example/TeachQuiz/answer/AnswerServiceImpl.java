package com.example.TeachQuiz.answer;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    public AnswerServiceImpl(AnswerRepository answerRepository, ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AnswerDto> getAnswers(Long id) {
        return answerRepository.getAnswers(id).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    @Override
    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }

    @Override
    public void saveAnswerList(List<Answer> answerList) {
        answerRepository.saveAll(answerList);
    }

    private AnswerDto convertToDto(Answer answer){
        AnswerDto answerDto = modelMapper.map(answer, AnswerDto.class);
        answerDto.setContent(answer.getAnswerContent());
        answerDto.setId(answer.getId());
        answerDto.setCorrect(answer.isCorrect());
        return answerDto;
    }
}