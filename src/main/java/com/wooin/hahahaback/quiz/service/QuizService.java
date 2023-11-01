package com.wooin.hahahaback.quiz.service;

import com.wooin.hahahaback.quiz.dto.QuizRequestDto;
import com.wooin.hahahaback.quiz.dto.QuizResponseDto;
import com.wooin.hahahaback.quiz.dto.QuizThumbResponseDto;
import com.wooin.hahahaback.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface QuizService {

    /**
     * 퀴즈 등록
     *
     * @param userDetails
     * @param requestDto  질문, 힌트, 정답, 유사정답들
     * @return PK번호를 포함한 퀴즈 정보 리턴
     */
    QuizResponseDto createQuiz(User userDetails, QuizRequestDto requestDto);


    /**
     * 퀴즈 한 객체 조회
     *
     * @param quizId 퀴즈 PK번호
     * @param user
     * @return PK번호를 포함한 퀴즈 정보 리턴
     */
    QuizResponseDto selectOneQuiz(Long quizId, User user);

    @Transactional(readOnly = true)
    Page<QuizThumbResponseDto> selectQuizPage(int page, int size, String sortBy, boolean isAsc);

    QuizResponseDto modifyQuiz(User user, Long quizId, QuizRequestDto requestDto);

    void deleteQuiz(Long quizId, User user);
}
