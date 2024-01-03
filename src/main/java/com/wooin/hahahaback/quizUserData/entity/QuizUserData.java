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

    Boolean isCheckedHint;
    Boolean isSolved;
    Boolean isLiked;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public QuizUserData(Quiz quiz, User user) {
        this.quiz = quiz;
        this.user = user;
        this.isCheckedHint = false;
        this.isSolved = false;
        this.isLiked = false;
    }

    public void checkHint() {
        this.isCheckedHint = true;
    }
    public void solveQuiz() {
        this.isSolved = true;
    }
    public void doLike() {
        this.isLiked = true;
        this.quiz.hitLikeCount();
    }
    public void cancleLike() {
        this.isLiked = false;
        this.quiz.cancleLikeCount();
    }
}
