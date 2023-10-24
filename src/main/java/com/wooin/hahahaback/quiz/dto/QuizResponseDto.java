package com.wooin.hahahaback.quiz.dto;

import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.reply.dto.ReplyResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class QuizResponseDto {

    private Long quizId;
    private String question;
    private String hint;
    private String answer;
    private String description;

    private String author;

//    private int totalScore;
    private List<ReplyResponseDto> replies;


    public QuizResponseDto(Quiz quiz) {

        this.quizId = quiz.getId();
        this.question = quiz.getQuestion();
        this.hint = quiz.getHint();
        this.answer = quiz.getAnswer();
        this.description = quiz.getDescription();
        this.author = quiz.getUser().getNickname();

        this.replies = quiz.getReplys().stream().map(ReplyResponseDto::new).toList();
    }
}
