package com.wooin.hahahaback.quizUserData.dto;

import com.wooin.hahahaback.quizUserData.entity.QuizUserData;
import lombok.Getter;

@Getter
public class QuizUserDataResponseDto {

    private Long id;

    private Boolean isShowHint;

    private Boolean isSolved;

    public QuizUserDataResponseDto(QuizUserData savedQuizUserData) {
        this.id = savedQuizUserData.getId();
        this.isShowHint = savedQuizUserData.getIsShowHint();
        this.isSolved = savedQuizUserData.getIsSolved();
    }
}
