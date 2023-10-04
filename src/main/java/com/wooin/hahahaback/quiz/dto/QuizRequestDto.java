package com.wooin.hahahaback.quiz.dto;

import lombok.Getter;

@Getter
public class QuizRequestDto {

    private String question;
    private String hint;
    private String answer;
    private String description;

}
