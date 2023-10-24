package com.wooin.hahahaback.like.controller;

import com.wooin.hahahaback.common.dto.ApiResponseDto;
import com.wooin.hahahaback.common.security.UserDetailsImpl;
import com.wooin.hahahaback.like.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/likes/quizzes/{id}")
    public ResponseEntity likeQuiz(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @PathVariable(value = "id") Long quizId) {

        likeService.likeQuiz(userDetails.getUser(), quizId);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.CREATED.value(), quizId+"번 퀴즈 좋아요를 눌렀습니다."));
    }

    @DeleteMapping("/likes/quizzes/{id}")
    public ResponseEntity likeCancleQuiz(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @PathVariable(value = "id") Long quizId) {

        likeService.likeCancleQuiz(userDetails.getUser(), quizId);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK.value(), quizId+"번 퀴즈 좋아요를 취소했습니다."));
    }

}
