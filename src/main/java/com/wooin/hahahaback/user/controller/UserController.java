package com.wooin.hahahaback.user.controller;

import com.wooin.hahahaback.common.dto.ApiResponseDto;
import com.wooin.hahahaback.user.dto.SignupRequestDto;
import com.wooin.hahahaback.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/users/login-page")
    public String loginPage(){
        return "login하세요";
    }



    @PostMapping("/users/signup") //@ModelAttribute는
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto, HttpServletResponse response){

        log.info("토큰값"+requestDto.getAdminToken());

        userService.signup(requestDto, response);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.CREATED.value(), "회원가입 완료."));
    }
}
