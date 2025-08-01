package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exam.entity.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {}
