package com.wooin.hahahaback.quiz.dto;

import com.wooin.hahahaback.quiz.entity.Quiz;
import lombok.Getter;

@Getter
public class QuizResponseDto {

    private Long quizId;
    private String question;
    private String hint;
    private String answer;
    private String description;

//    private String submitter;

//    private int totalScore;


    public QuizResponseDto(Quiz quiz) {

        this.quizId = quiz.getId();
        this.question = quiz.getQuestion();
        this.hint = quiz.getHint();
        this.answer = quiz.getAnswer();
        this.description = quiz.getDescription();

//        this.submitter = quiz.getUser().getNickname();

//        this.totalScore = 0;
    }
}
