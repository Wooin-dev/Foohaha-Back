package com.wooin.hahahaback.mypage.controller;

import com.wooin.hahahaback.common.dto.ApiResponseDto;
import com.wooin.hahahaback.common.security.UserDetailsImpl;
import com.wooin.hahahaback.mypage.dto.EditMyProfileRequestDto;
import com.wooin.hahahaback.mypage.dto.MyOverviewResponseDto;
import com.wooin.hahahaback.mypage.dto.MyProfileResponseDto;
import com.wooin.hahahaback.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;


    @GetMapping("/my-page/my-profile")
    public ResponseEntity<MyProfileResponseDto> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        MyProfileResponseDto responseDto = myPageService.getMyProfile(userDetails.getUser());

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/my-page/edit-profile")
    public ResponseEntity<ApiResponseDto> editMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @RequestBody EditMyProfileRequestDto requestDto) {
        myPageService.editMyProfile(userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.CREATED.value(), "수정 완료."));
    }

    @GetMapping("/my-page/overview")
    public ResponseEntity<MyOverviewResponseDto> getMyOverview(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        MyOverviewResponseDto responseDto = myPageService.getMyOverview(userDetails.getUser());

        return ResponseEntity.ok().body(responseDto);
    }


}
