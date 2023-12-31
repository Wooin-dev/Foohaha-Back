package com.wooin.hahahaback.reply.repository;

import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Integer countByQuiz(Quiz quiz);
}
