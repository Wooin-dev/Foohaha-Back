package com.wooin.hahahaback.quizreaction.controller;

import com.wooin.hahahaback.common.dto.ApiResponseDto;
import com.wooin.hahahaback.common.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class QuizScoreController {

    @GetMapping("/quiz-reaction/{quizId}/{score}")
    public ResponseEntity<ApiResponseDto> hitQuiz(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable Long quizId,
                                                  @PathVariable String score) {

//        QuizScoreResponseDto result =


        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.CREATED.value(), ""));
    }
}
