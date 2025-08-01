package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exam.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {}
