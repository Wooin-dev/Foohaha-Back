package com.wooin.hahahaback.like.repository;

import com.wooin.hahahaback.like.entity.QuizLike;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizLikeRepository extends JpaRepository<QuizLike, Long> {
    Boolean existsByUserAndQuiz_Id(User user, Long id);
    Long deleteByQuizAndUser(Quiz quiz, User user);
}
