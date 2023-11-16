package com.wooin.hahahaback.quizUserData.repository;

import com.wooin.hahahaback.quizUserData.entity.QuizUserData;
import com.wooin.hahahaback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizUserDataRepository extends JpaRepository<QuizUserData, Long> {
    boolean existsByUserAndQuiz_Id(User user, Long id);
    Optional<QuizUserData> findByUserAndQuiz_Id(User user, Long quizId);
}
