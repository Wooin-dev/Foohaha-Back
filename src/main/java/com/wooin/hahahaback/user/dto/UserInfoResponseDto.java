package com.wooin.hahahaback.user.dto;

import com.wooin.hahahaback.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

    private Long id;
    private Long kakaoId;
    private String username;
    private String nickname;
    private String email;

    public UserInfoResponseDto(User user) {
        this.id = user.getId();
        this.kakaoId = user.getKakaoId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
