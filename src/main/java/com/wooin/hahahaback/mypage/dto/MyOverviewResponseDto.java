package com.wooin.hahahaback.mypage.dto;

import com.wooin.hahahaback.quiz.dto.QuizResponseDto;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.userdata.entity.UserData;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyOverviewResponseDto {

    private Integer showQuizCnt;
    private Integer showHintCnt;
    private Integer solveQuizCnt;
    private Integer createQuizCnt;

    private List<QuizResponseDto> createQuizzes;


    public MyOverviewResponseDto(UserData foundUserData, List<Quiz> foundQuizzes) {
        this.showQuizCnt = foundUserData.getShowQuizCnt();
        this.showHintCnt = foundUserData.getShowHintCnt();
        this.solveQuizCnt = foundUserData.getSolveQuizCnt();
        this.createQuizCnt = foundUserData.getCreateQuizCnt();

        this.createQuizzes = foundQuizzes.stream().map(QuizResponseDto::new).toList();
    }
}
