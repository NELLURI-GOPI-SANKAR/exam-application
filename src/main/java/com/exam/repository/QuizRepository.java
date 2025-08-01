package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exam.entity.QuizEntity;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
    QuizEntity findByQuizName(String quizName); 
}
