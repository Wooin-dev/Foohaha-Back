package com.wooin.hahahaback.mypage.dto;

import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.userdata.entity.UserData;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyProfileResponseDto {

    private String email;
    private String nickname;
    private LocalDateTime createdAt;

    private Integer createdQuizCnt;


    public MyProfileResponseDto(User user, UserData userData) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.createdAt = user.getCreatedAt();
        this.createdQuizCnt = userData.getCreateQuizCnt();
    }
}
