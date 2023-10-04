package com.wooin.hahahaback.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Size(min = 4, max = 10) //문자열이라 Min Max대신 Size 시도
    @Pattern(regexp = "^[a-z0-9]+$")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z]+[0-9]+[!@#$%^&*()+_-]+$")
    private String password;

    @Email
    private String email;

    @Size(min = 2, max = 12)
    private String nickname;

    private boolean admin;
    private String adminToken;
}