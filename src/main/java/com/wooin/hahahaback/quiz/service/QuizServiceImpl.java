package com.wooin.hahahaback.quiz.service;

import com.wooin.hahahaback.common.exception.NoAuthorizedException;
import com.wooin.hahahaback.common.exception.NotFoundException;
import com.wooin.hahahaback.quiz.dto.QuizRequestDto;
import com.wooin.hahahaback.quiz.dto.QuizResponseDto;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.quiz.repository.QuizRepository;
import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.user.repository.UserRepository;
import com.wooin.hahahaback.userdata.repository.UserDataRepository;
import com.wooin.hahahaback.userdata.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService{

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final UserDataService userDataService;
    private final UserDataRepository userDataRepository;


    @Override
    @Transactional
    public QuizResponseDto createQuiz(User user, QuizRequestDto requestDto) {

        Quiz newQuiz = new Quiz(requestDto, user);
        Quiz savedQuiz = quizRepository.save(newQuiz);

        userDataService.findUserDataByUser(user).countCreateQuiz();

        return new QuizResponseDto(savedQuiz);
    }

    @Override
    @Transactional
    public QuizResponseDto selectOneQuiz(Long quizId, User user) {
        userDataService.findUserDataByUser(user).countShowQuiz();
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
    public QuizResponseDto modifyQuiz(User user, Long quizId, QuizRequestDto requestDto) {
        Quiz foundQuiz = findQuizById(quizId);

        checkUserAuthorization(user, foundQuiz);

        foundQuiz.modifyQuiz(requestDto);
        return new QuizResponseDto(foundQuiz);
    }




    @Override
    @Transactional
    public void deleteQuiz(Long quizId, User user) {

        Quiz foundQuiz = findQuizById(quizId);

        checkUserAuthorization(user, foundQuiz);

        quizRepository.deleteById(quizId);


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
    private void checkUserAuthorization(User user, Quiz foundQuiz) {
        if (!user.getId().equals(foundQuiz.getUser().getId())) {
            throw new NoAuthorizedException("권한이 없습니다.");
        }
    }
}
