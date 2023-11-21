package com.wooin.hahahaback.mypage.controller;

import com.wooin.hahahaback.common.dto.ApiResponseDto;
import com.wooin.hahahaback.common.security.UserDetailsImpl;
import com.wooin.hahahaback.mypage.dto.EditMyProfileRequestDto;
import com.wooin.hahahaback.mypage.dto.MyProfileResponseDto;
import com.wooin.hahahaback.mypage.service.MyPageService;
import com.wooin.hahahaback.quiz.dto.QuizThumbResponseDto;
import com.wooin.hahahaback.quiz.service.QuizServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final QuizServiceImpl quizService;


    @GetMapping("/my-profile")
    public ResponseEntity<MyProfileResponseDto> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        MyProfileResponseDto responseDto = myPageService.getMyProfile(userDetails.getUser());

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/edit-profile")
    public ResponseEntity<ApiResponseDto> editMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @RequestBody EditMyProfileRequestDto requestDto) {
        myPageService.editMyProfile(userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.CREATED.value(), "수정 완료."));
    }

    @GetMapping("/my-quizzes/created")
    public ResponseEntity<Page<QuizThumbResponseDto>> selectMyCreatedQuizzes(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                           @RequestParam("page") int page,
                                                                           @RequestParam("size") int size,
                                                                           @RequestParam("sortBy") String sortBy,
                                                                           @RequestParam("isAsc") boolean isAsc) {

        Page<QuizThumbResponseDto> responseDto = quizService.selectMyCreatedQuizzes(userDetails.getUser(), page-1, size, sortBy, isAsc);

        return ResponseEntity.ok().body(responseDto);
    }

}
