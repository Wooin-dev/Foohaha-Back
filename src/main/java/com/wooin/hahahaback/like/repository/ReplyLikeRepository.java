package com.wooin.hahahaback.like.repository;

import com.wooin.hahahaback.like.entity.ReplyLike;
import com.wooin.hahahaback.reply.entity.Reply;
import com.wooin.hahahaback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
    long deleteByUserAndReply(User user, Reply reply);
    boolean existsByUserAndReply_Id(User user, Long replyId);
}