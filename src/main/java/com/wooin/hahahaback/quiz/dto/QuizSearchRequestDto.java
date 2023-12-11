package com.wooin.hahahaback.quiz.dto;

import lombok.Getter;

@Getter
public class QuizSearchRequestDto {

    private Integer page;
    private Integer size;
    private String sortBy;
    private Boolean isAsc;

    private String question;
    private String author;
}
