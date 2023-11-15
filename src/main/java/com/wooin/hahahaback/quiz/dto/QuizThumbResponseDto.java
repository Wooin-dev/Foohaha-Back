package com.wooin.hahahaback.quiz.dto;

import com.wooin.hahahaback.quiz.entity.Quiz;
import lombok.Getter;
import lombok.Setter;

@Getter
public class QuizThumbResponseDto {

    private Long quizId;
    private String question;
    private String author;

    @Setter
    private Integer cntReplies;

//    @Setter
    private Integer cntLikes;



    public QuizThumbResponseDto(Quiz quiz) {

        this.quizId = quiz.getId();
        this.question = quiz.getQuestion();
        this.author = quiz.getUser().getNickname();
        this.cntLikes = quiz.getLikeCount();
    }
}
