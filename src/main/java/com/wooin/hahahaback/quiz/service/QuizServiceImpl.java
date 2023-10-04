package com.wooin.hahahaback.quiz.service;

import com.wooin.hahahaback.common.exception.NotFoundException;
import com.wooin.hahahaback.quiz.dto.QuizRequestDto;
import com.wooin.hahahaback.quiz.dto.QuizResponseDto;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.quiz.repository.QuizRepository;
import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService{

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public QuizResponseDto createQuiz(QuizRequestDto requestDto) {

        Quiz newQuiz = new Quiz(requestDto);
        Quiz savedQuiz = quizRepository.save(newQuiz);

        return new QuizResponseDto(savedQuiz);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizResponseDto selectOneQuiz(Long quizId) {
        return new QuizResponseDto(findQuizById(quizId));
    }


    @Override
    @Transactional(readOnly = true)
    public List<QuizResponseDto> selectAllQuiz() { //todo 페이징 처리

        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream().map(QuizResponseDto::new).toList();
    }

    @Override
    @Transactional
    public QuizResponseDto modifyQuiz(Long quizId, QuizRequestDto requestDto) {

        Quiz foundQuiz = findQuizById(quizId);
//        User foundUser = findUserById(user.getId());

//        if (!foundUser.getNickname().equals(foundQuiz.getUser().getNickname())) {
//            throw new NoAuthorizedException("수정할 수 없는 사용자입니다.");
//        }
        foundQuiz.modifyQuiz(requestDto);
        return new QuizResponseDto(foundQuiz);
    }


    @Override
    @Transactional
    public void deleteQuiz(Long quizId) {

        Quiz foundQuiz = findQuizById(quizId);

        quizRepository.deleteById(quizId);

//        if (!user.getNickname().equals(foundQuiz.getUser().getNickname())) {
//            throw new NoAuthorizedException("수정할 수 없는 사용자입니다.");
//        }



    }


    //// Private 메소드

    private Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElseThrow(()
                -> new NotFoundException(quizId + "번의 퀴즈를 찾을 수 없습니다."));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException(userId + " : 유저를 찾을 수 없습니다."));
    }


}
