package com.wooin.hahahaback.reply.controller;

import com.wooin.hahahaback.common.dto.ApiResponseDto;
import com.wooin.hahahaback.common.security.UserDetailsImpl;
import com.wooin.hahahaback.reply.dto.ReplyRequestDto;
import com.wooin.hahahaback.reply.dto.ReplyResponseDto;
import com.wooin.hahahaback.reply.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/quizzes/{id}/replies")
    public ResponseEntity<ReplyResponseDto> createReply(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody ReplyRequestDto requestDto,
                                         @PathVariable(value = "id") Long quizId) {

        ReplyResponseDto responseDto = replyService.createReply(userDetails.getUser(), requestDto, quizId);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/replies/{id}")
    public ResponseEntity<ReplyResponseDto> modifyReply(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @RequestBody ReplyRequestDto requestDto,
                                                        @PathVariable(value = "id") Long replyId) {

        ReplyResponseDto responseDto = replyService.modifyReply(userDetails.getUser(), requestDto, replyId);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/replies/{id}")
    public ResponseEntity<ApiResponseDto> deleteReply(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable(value = "id") Long replyId) {

        replyService.deleteReply(userDetails.getUser(), replyId);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK.value(), "댓글이 삭제되었습니다."));
    }

}
