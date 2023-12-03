package com.wooin.hahahaback.mypage.repository;

import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MyPageRepositoryCustom {
    Page<Quiz> selectMyCheckedHintQuizzes(User user, Pageable pageable);
}
