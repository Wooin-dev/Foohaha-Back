package com.wooin.hahahaback.quiz.repository;

import com.wooin.hahahaback.quiz.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepositoryCustom {
    Page<Quiz> searchQuizzes(Pageable pageable, String author, String question);
}
