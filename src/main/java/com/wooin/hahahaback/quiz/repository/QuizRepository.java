package com.wooin.hahahaback.quiz.repository;

import com.wooin.hahahaback.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
