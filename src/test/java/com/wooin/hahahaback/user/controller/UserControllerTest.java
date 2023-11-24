package com.wooin.hahahaback.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wooin.hahahaback.common.config.WebSecurityConfig;
import com.wooin.hahahaback.common.security.UserDetailsImpl;
import com.wooin.hahahaback.mvc.MockSpringSecurityFilter;
import com.wooin.hahahaback.user.dto.SignupRequestDto;
import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.user.entity.UserRoleEnum;
import com.wooin.hahahaback.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class,
            excludeFilters = { //Security 환경에서의 WebSecurity 필터를 제외.
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes= WebSecurityConfig.class
                )})
@Slf4j(topic = "UserControllerTest")
class UserControllerTest {

    //@BeforeEach단에서 init시켜줄 예정이라 주입받아오지 않는다.
    private MockMvc mvc;
    private Principal mockPrincipal;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter())) //만들어둔 Mock필터를 여기서 추가해준다
                .build();
    }

    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        // 인증을 통해 생성되는 UserDetailsImpl을 직접 만들어주는 과정
        // 이 메소드가 필요한 테스트는 테스트 상단에 호출하여 초기화 사용
        log.info("Principal 생성");
        String username = "sollertia4351";
        String password = "robbie1234";
        String email = "sollertia@sparta.com";
        String nickname = "nickTest";
        UserRoleEnum role = UserRoleEnum.USER;
        User testUser = new User(username, password, email, nickname, role);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

    @Test
    @DisplayName("회원가입 요청")
    void testSignUp() throws Exception {
        ////Given
        String username = "sollertia";
        String password = "Arobbi1234!";
        String email = "sollertia@sparta.com";
        String nickname = "nickTest";

        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();

        String signupInfo = objectMapper.writeValueAsString(signupRequestDto);

        ////WHEN - THEN
        mvc.perform(post("/api/users/signup")
                        .content(signupInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }



}