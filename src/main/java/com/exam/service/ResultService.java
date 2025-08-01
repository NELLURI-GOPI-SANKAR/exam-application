package com.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exam.entity.Result;
import com.exam.repository.ResultRepository;

import java.time.LocalDateTime;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    public void saveResult(String username, String quizName, int score) {
        Result result = new Result(username, quizName, score, LocalDateTime.now());
        resultRepository.save(result);
    }
}
