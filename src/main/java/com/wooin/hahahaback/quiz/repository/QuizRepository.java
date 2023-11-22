package com.wooin.hahahaback.quiz.repository;

import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Page<Quiz> findByQuizUserDatas_UserAndQuizUserDatas_IsShowHintTrueAndQuizUserDatas_IsSolvedFalse(User user, Pageable pageable);
    Page<Quiz> findByQuizUserDatas_UserAndQuizUserDatasNotEmptyAndQuizUserDatas_IsSolvedFalse(User user, Pageable pageable);
    Page<Quiz> findByQuizUserDatas_IsSolvedTrueAndQuizUserDatas_User(User user, Pageable pageable);
    Page<Quiz> findAllByQuizLikes_User(User user, Pageable pageable);
    Page<Quiz> findAllByUser(User user, Pageable pageable);
}
