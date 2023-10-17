package com.wooin.hahahaback.common.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Getter
@RedisHash(value = "tokenInfo", timeToLive = 14 * 24 * 60 * 60) // 일*시*분*초 14일로 설정해 둠.
public class TokenDto {

    @Id
    private String username;
    private String accessToken;
    private String refreshToken;

}
