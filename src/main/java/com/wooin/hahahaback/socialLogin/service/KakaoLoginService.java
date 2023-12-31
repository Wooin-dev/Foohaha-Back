package com.wooin.hahahaback.socialLogin.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wooin.hahahaback.common.jwt.JwtUtil;
import com.wooin.hahahaback.socialLogin.dto.KakaoUserInfoDto;
import com.wooin.hahahaback.user.dto.UserInfoResponseDto;
import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.user.entity.UserRoleEnum;
import com.wooin.hahahaback.user.repository.UserRepository;
import com.wooin.hahahaback.userdata.service.UserDataService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j(topic = "KAKAO Login")
@Service
public class KakaoLoginService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDataService userDataService;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    @Value("${front-end-base-uri}")
    private final String FRONT_BASE_URI;

    public KakaoLoginService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserDataService userDataService, RestTemplate restTemplate, JwtUtil jwtUtil,
                             @Value("${front-end-base-uri}") String FRONT_BASE_URI) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDataService = userDataService;
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;
        this.FRONT_BASE_URI = FRONT_BASE_URI;
    }

    public UserInfoResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException { //String code는 카카오로부터 받은 인가 코드
        // 1. "인가 코드"로 "액세스 토큰" 요청. 액세스 토큰은 카카오 사용자 정보를 가져오기 위한 코드
        String accessToken = getToken(code);
        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        // 3. 필요시 회원가입 아니라면 바로 user가져오기.
        User kakaouser = registerKakaoUserIfNeeded(kakaoUserInfo);
        // 4. JWT 토큰 반환
        String[] tokens = jwtUtil.addJwtToCookie(kakaouser, response);

        log.info("토큰정보" + tokens);

        return new UserInfoResponseDto(kakaouser);
    }


    private String getToken(String code) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "c23875cfdc64342735efd50df1d7af76");
        body.add("redirect_uri",FRONT_BASE_URI+"/api/users/kakao/callback");
        body.add("code", code);

        log.info(FRONT_BASE_URI+" : front base uri");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri) // POST방식
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 만들기
        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기 // 여기서 응답을 받아옴.
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email;
        try {
            email = jsonNode.get("kakao_account")
                    .get("email").asText();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("이메일 정보를 가져올 수 없습니다.");
        }

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfoDto(id, nickname, email);
    }

    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);

        if (kakaoUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String kakaoEmail = kakaoUserInfo.getEmail();
            User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = kakaoUserInfo.getEmail();
                String nickname = kakaoUserInfo.getNickname();

                kakaoUser = new User("kakao" + kakaoUserInfo.getNickname(), encodedPassword, email, nickname, UserRoleEnum.USER, kakaoId);
            }
            userRepository.save(kakaoUser);
            userDataService.createData(kakaoUser);
        }
        return kakaoUser;
    }

}