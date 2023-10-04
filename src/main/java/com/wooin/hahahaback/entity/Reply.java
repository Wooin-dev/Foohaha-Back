package com.wooin.hahahaback.entity;

import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    Quiz quiz;
}
