package com.wooin.hahahaback.like.controller;

import com.wooin.hahahaback.common.dto.ApiResponseDto;
import com.wooin.hahahaback.common.security.UserDetailsImpl;
import com.wooin.hahahaback.like.service.LikeService;
import com.wooin.hahahaback.quizUserData.service.QuizUserDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;
    private final QuizUserDataService quizUserDataService;

    public LikeController(LikeService likeService, QuizUserDataService quizUserDataService) {
        this.likeService = likeService;
        this.quizUserDataService = quizUserDataService;
    }

    @GetMapping("/likes/quizzes/{id}/do-like")
    public ResponseEntity doLikeQuiz(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @PathVariable(value = "id") Long quizId) {

        quizUserDataService.doLikeQuiz(userDetails.getUser(), quizId);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.CREATED.value(), quizId+"번 퀴즈 좋아요를 눌렀습니다."));
    }

    @GetMapping("/likes/quizzes/{id}/cancle-like")
    public ResponseEntity likeCancleQuiz(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @PathVariable(value = "id") Long quizId) {

        quizUserDataService.cancleLikeQuiz(userDetails.getUser(), quizId);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK.value(), quizId+"번 퀴즈 좋아요를 취소했습니다."));
    }



    @GetMapping("/likes/replies/is-liked/{id}")
    public ResponseEntity<Boolean> isLikedReply(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @PathVariable(value = "id") Long replyId) {

        Boolean result = likeService.isLikedReply(userDetails.getUser(), replyId);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/likes/replies/{id}")
    public ResponseEntity likeReply(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @PathVariable(value = "id") Long replyId) {

        likeService.likeReply(userDetails.getUser(), replyId);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.CREATED.value(), replyId+"번 댓글 좋아요를 눌렀습니다."));
    }

    @DeleteMapping("/likes/replies/{id}")
    public ResponseEntity likeCancleReply(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @PathVariable(value = "id") Long replyId) {

        likeService.likeCancleReply(userDetails.getUser(), replyId);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK.value(), replyId+"번 댓글 좋아요를 취소했습니다."));
    }

}
