package com.wooin.hahahaback.common.refreshtoken.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Getter
@RedisHash(value = "refreshToken", timeToLive = 14*24*60*60) // 일*시*분*초 14일로 설정해 둠.
public class TokenDto {
    @Id
    private Long userId ;

    private String refreshToken;

    private  String accessToken;
}
