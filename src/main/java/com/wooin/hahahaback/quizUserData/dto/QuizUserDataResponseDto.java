package com.wooin.hahahaback.quizUserData.dto;

import com.wooin.hahahaback.quizUserData.entity.QuizUserData;
import lombok.Getter;

@Getter
public class QuizUserDataResponseDto {

    private Long id;

    private Boolean isShowHint;

    private Boolean isSolved;

    private Boolean isLiked;

    public QuizUserDataResponseDto(QuizUserData savedQuizUserData) {
        this.id = savedQuizUserData.getId();
        this.isShowHint = savedQuizUserData.getIsCheckedHint();
        this.isSolved = savedQuizUserData.getIsSolved();
        this.isLiked = savedQuizUserData.getIsLiked();
    }
}
