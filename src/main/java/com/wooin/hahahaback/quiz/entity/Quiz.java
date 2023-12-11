package com.wooin.hahahaback.quiz.entity;

import com.wooin.hahahaback.common.entity.Timestamped;
import com.wooin.hahahaback.like.entity.QuizLike;
import com.wooin.hahahaback.quiz.dto.QuizRequestDto;
import com.wooin.hahahaback.quizUserData.entity.QuizUserData;
import com.wooin.hahahaback.reply.entity.Reply;
import com.wooin.hahahaback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question", nullable = false, length = 50)
    private String question;

    @Column(name = "hint")
    private String hint;

    @Column(name = "answer", nullable = false, length = 500)
    private String answer;

    @Column
    private String description;

    @Column
    private Integer likeCount;


    ////연관관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "quiz", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Reply> replys = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QuizLike> quizLikes = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QuizUserData> quizUserDatas = new ArrayList<>();


    ////생성자

    @Builder
    public Quiz(String question, String hint, String answer, String description, User user, List<Reply> replys, List<QuizLike> quizLikes, List<QuizUserData> quizUserDatas) {
        this.question = question;
        this.hint = hint;
        this.answer = answer;
        this.description = description;
        this.user = user;
        this.replys = replys;
        this.quizLikes = quizLikes;
        this.quizUserDatas = quizUserDatas;

        this.likeCount = 0;
    }

    public Quiz(QuizRequestDto requestDto, User user) {

        this.question = requestDto.getQuestion();
        this.hint = requestDto.getHint();
        this.answer = requestDto.getAnswer();
        this.description = requestDto.getDescription();
        this.user = user;
        this.likeCount = 0;
    }

    public void modifyQuiz(QuizRequestDto requestDto) {
        if (!requestDto.getQuestion().isBlank()) this.question = requestDto.getQuestion();
        if (!requestDto.getHint().isBlank()) this.hint = requestDto.getHint();
        if (!requestDto.getAnswer().isBlank()) this.answer = requestDto.getAnswer();
        if (!requestDto.getDescription().isBlank()) this.description = requestDto.getDescription();
    }

    public void hitLikeCount() {
        //todo 이전 버전의 객체와 맞추기 위한 코드도 생각해볼 필요가 있다.
        if (this.likeCount == null) {
            this.likeCount = 0;
        }
        this.likeCount++;
    }

    public void cancleLikeCount() {
        this.likeCount--;
    }
}
