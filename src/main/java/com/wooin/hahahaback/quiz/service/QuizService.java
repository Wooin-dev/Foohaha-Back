package com.wooin.hahahaback.quiz.service;

import com.wooin.hahahaback.quiz.dto.QuizRequestDto;
import com.wooin.hahahaback.quiz.dto.QuizResponseDto;
import com.wooin.hahahaback.user.entity.User;

import java.util.List;

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
     * @param quizId    퀴즈 PK번호
     * @return          PK번호를 포함한 퀴즈 정보 리턴
     */
    QuizResponseDto selectOneQuiz(Long quizId);

    List<QuizResponseDto> selectAllQuiz();

    QuizResponseDto modifyQuiz(User user, Long quizId, QuizRequestDto requestDto);

    void deleteQuiz(Long quizId, User user);
}
