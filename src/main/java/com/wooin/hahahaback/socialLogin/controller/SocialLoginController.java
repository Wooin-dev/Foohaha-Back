package com.wooin.hahahaback.socialLogin.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.wooin.hahahaback.socialLogin.service.KakaoLoginService;
import com.wooin.hahahaback.user.dto.UserInfoResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class SocialLoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/users/kakao/callback") //버튼을 누르게 되면 카카오 서버로부터 리다이렉트되어 인가 코드를 전달받게됨. 해당 URL은 카카오 로그인 홈페이지에서 등록해뒀음.
    public ResponseEntity<UserInfoResponseDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        log.info(code);

        UserInfoResponseDto userInfoResponseDto = kakaoLoginService.kakaoLogin(code, response);
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token); //check 토큰은 헤더에 넣어서 전달하는 방식.

        return ResponseEntity.ok().body(userInfoResponseDto);
    }
}