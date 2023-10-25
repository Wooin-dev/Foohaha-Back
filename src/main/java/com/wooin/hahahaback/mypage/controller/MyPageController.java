package com.wooin.hahahaback.mypage.controller;

import com.wooin.hahahaback.common.security.UserDetailsImpl;
import com.wooin.hahahaback.mypage.dto.MyOverviewResponseDto;
import com.wooin.hahahaback.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;


    @GetMapping("/my-page/overview")
    public ResponseEntity<MyOverviewResponseDto> getMyOverview(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        MyOverviewResponseDto responseDto = myPageService.getMyOverview(userDetails.getUser());

        return ResponseEntity.ok().body(responseDto);
    }
}
