package com.wooin.hahahaback.quizUserData.controller;

import com.wooin.hahahaback.common.dto.ApiResponseDto;
import com.wooin.hahahaback.common.security.UserDetailsImpl;
import com.wooin.hahahaback.quizUserData.dto.QuizUserDataResponseDto;
import com.wooin.hahahaback.quizUserData.service.QuizUserDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz-user-data")
public class QuizUserDataController {

    private final QuizUserDataService quizUserDataService;

    public QuizUserDataController(QuizUserDataService quizUserDataService) {
        this.quizUserDataService = quizUserDataService;
    }

    @GetMapping("/show-quiz/{id}")
    public ResponseEntity<QuizUserDataResponseDto> showQuiz(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable(value = "id") Long quizId ) {

        QuizUserDataResponseDto result = quizUserDataService.getQuizUserData(userDetails.getUser(), quizId);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/show-hint/{id}")
    public ResponseEntity<ApiResponseDto> showHint(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable(value = "id") Long quizId) {

        quizUserDataService.showHint(userDetails.getUser(), quizId);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK.value(), quizId+"번 퀴즈의 힌트를 확인했습니다."));
    }

    @GetMapping("/solve-quiz/{id}")
    public ResponseEntity<ApiResponseDto> solveQuiz(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable(value = "id") Long quizId) {

        quizUserDataService.solveQuiz(userDetails.getUser(), quizId);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK.value(), quizId+"번 퀴즈를 풀었습니다."));
    }

}
