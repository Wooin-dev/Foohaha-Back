package com.wooin.hahahaback.like.entity;

import com.wooin.hahahaback.reply.entity.Reply;
import com.wooin.hahahaback.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Reply reply;

    @ManyToOne
    private User user;

    @Builder
    public ReplyLike(Reply reply, User user) {
        this.reply = reply;
        this.user = user;
    }
}
