package com.wooin.hahahaback.common.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wooin.hahahaback.common.dto.ApiResponseDto;
import com.wooin.hahahaback.common.refreshtoken.dto.TokenDto;
import com.wooin.hahahaback.common.refreshtoken.repository.RefreshTokenRepository;
import com.wooin.hahahaback.common.security.UserDetailsImpl;
import com.wooin.hahahaback.user.dto.LoginRequestDto;
import com.wooin.hahahaback.user.entity.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        setFilterProcessesUrl("/api/users/login"); //aha JSON방식으로 들어옴...
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate( //getAuthenticationManager는 상속받았기에 바로 사용할 수 있다.
                    new UsernamePasswordAuthenticationToken( // 차례대로 Principal, Credential, Authority
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        // refactor 토큰을 한번에 처리할 수 있게 합칠 수도 있지 않을까?
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
        Long id = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getId();

        String accessToken = jwtUtil.createToken(username, role);
        String refreshToken = jwtUtil.createRefreshToken(username, role, id); //refresh token 생성

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken); //헤더에 추가로 전달. todo 클라이언트에서 헤더값을 저장해주는 코드 필요.

        refreshTokenRepository.save(new TokenDto(((UserDetailsImpl) authResult.getPrincipal()).getUser().getId(),
                refreshToken, accessToken));

        log.info("accessToken : " + accessToken);
        log.info("refreshToken : " + refreshToken);

        response.setStatus(200);
        response.setContentType("application/json");
        String result = new ObjectMapper().writeValueAsString(new ApiResponseDto(HttpStatus.OK.value(), "Login Success"));

        response.getOutputStream().print(result);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        String result = new ObjectMapper().writeValueAsString(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "Login Failed"));

        response.getOutputStream().print(result);
    }
}