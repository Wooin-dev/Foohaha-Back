package com.wooin.hahahaback.like.entity;

import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private User user;

    @Builder
    public QuizLike(Quiz quiz, User user) {
        this.quiz = quiz;
        this.user = user;
    }
}
