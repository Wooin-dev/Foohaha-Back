package com.wooin.hahahaback.quiz.repository;

import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.reply.entity.Reply;
import com.wooin.hahahaback.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    long countByReplys(Reply replys);
    List<Quiz> findByUser(User user);

    Page<Quiz> findAllByUser(User user, Pageable pageable);
}
