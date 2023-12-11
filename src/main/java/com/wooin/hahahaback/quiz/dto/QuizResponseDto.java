package com.wooin.hahahaback.quiz.dto;

import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.reply.dto.ReplyResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizResponseDto {

    private Long quizId;
    private String question;
    private String hint;
    private String answer;
    private String description;

    private LocalDateTime createdAt;

    private Integer likesCnt;

    private Long authorId;
    private String author;

//    private int totalScore;
    private List<ReplyResponseDto> replies;


    public QuizResponseDto(Quiz quiz) {

        this.quizId = quiz.getId();
        this.question = quiz.getQuestion();
        this.hint = quiz.getHint();
        this.answer = quiz.getAnswer();
        this.description = quiz.getDescription();
        this.createdAt = quiz.getCreatedAt();

        this.likesCnt = quiz.getLikeCount();

        this.authorId = quiz.getUser().getId();
        this.author = quiz.getUser().getNickname();

        this.replies = quiz.getReplys().stream().map(ReplyResponseDto::new).toList();
    }
}
