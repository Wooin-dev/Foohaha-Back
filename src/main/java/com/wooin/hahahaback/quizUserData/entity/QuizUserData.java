package com.wooin.hahahaback.quizUserData.entity;

import com.wooin.hahahaback.common.entity.Timestamped;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizUserData extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Boolean isShowHint;
    Boolean isSolved;

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private User user;

    @Builder
    public QuizUserData(Quiz quiz, User user) {
        this.quiz = quiz;
        this.user = user;
        this.isShowHint = false;
        this.isSolved = false;
    }

    public void showHint() {
        this.isShowHint = true;
    }
    public void solveQuiz() {
        this.isSolved = true;
    }
}
