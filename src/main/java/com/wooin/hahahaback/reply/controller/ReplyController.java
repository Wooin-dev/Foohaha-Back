package com.wooin.hahahaback.reply.controller;

import com.wooin.hahahaback.common.security.UserDetailsImpl;
import com.wooin.hahahaback.reply.dto.ReplyRequestDto;
import com.wooin.hahahaback.reply.dto.ReplyResponseDto;
import com.wooin.hahahaback.reply.service.ReplyService;
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

        ReplyResponseDto result = replyService.createReply(userDetails.getUser(), requestDto, quizId);

        return ResponseEntity.ok().body(result);
    }


}
