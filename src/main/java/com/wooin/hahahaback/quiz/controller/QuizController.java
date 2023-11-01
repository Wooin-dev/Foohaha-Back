package com.wooin.hahahaback.quiz.controller;

import com.wooin.hahahaback.common.dto.ApiResponseDto;
import com.wooin.hahahaback.common.security.UserDetailsImpl;
import com.wooin.hahahaback.quiz.dto.QuizRequestDto;
import com.wooin.hahahaback.quiz.dto.QuizResponseDto;
import com.wooin.hahahaback.quiz.dto.QuizThumbResponseDto;
import com.wooin.hahahaback.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/quizzes")
    public ResponseEntity<ApiResponseDto> createQuiz(
          @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody QuizRequestDto requestDto) {

        QuizResponseDto responseDto = quizService.createQuiz(userDetails.getUser(), requestDto);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.CREATED.value(),
                responseDto.getQuestion() + " 퀴즈가 등록되었습니다."));
    }

    @GetMapping("/quizzes")
    public ResponseEntity<Page<QuizThumbResponseDto>> selectQuizList(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {

        Page<QuizThumbResponseDto> responseDtos = quizService.selectQuizPage(page-1, size, sortBy, isAsc);

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<QuizResponseDto> selectOneQuiz(
            @AuthenticationPrincipal @Nullable UserDetailsImpl userDetails,
            @PathVariable Long quizId) {

        QuizResponseDto responseDto = quizService.selectOneQuiz(quizId, userDetails==null ? null : userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("/quizzes/{quizId}")
    public ResponseEntity<QuizResponseDto> modifyQuiz(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long quizId,
            @RequestBody QuizRequestDto requestDto) throws IOException {

        QuizResponseDto responseDto = quizService.modifyQuiz(userDetails.getUser(), quizId, requestDto);

        return ResponseEntity.ok(responseDto);
    }


    @DeleteMapping("/quizzes/{quizId}")
    public ResponseEntity<ApiResponseDto> deleteQuiz(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long quizId) {

        quizService.deleteQuiz(quizId, userDetails.getUser());

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK.value(), quizId + "번 퀴즈가 삭제되었습니다."));
    }


}
