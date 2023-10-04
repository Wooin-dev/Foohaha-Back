package com.wooin.hahahaback.quiz.entity;

import com.wooin.hahahaback.user.entity.User;
import jakarta.persistence.*;

@Entity
public class QuizLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private User user;
}
