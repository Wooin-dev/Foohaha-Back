package com.wooin.hahahaback.quizUserData.service;

import com.wooin.hahahaback.common.exception.NotFoundException;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.quiz.repository.QuizRepository;
import com.wooin.hahahaback.quizUserData.dto.QuizUserDataResponseDto;
import com.wooin.hahahaback.quizUserData.entity.QuizUserData;
import com.wooin.hahahaback.quizUserData.repository.QuizUserDataRepository;
import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.userdata.service.UserDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizUserDataService {

    private final QuizUserDataRepository quizUserDataRepository;
    private final QuizRepository quizRepository;
    private final UserDataService userDataService;

    public QuizUserDataService(QuizUserDataRepository quizUserDataRepository, QuizRepository quizRepository, UserDataService userDataService) {
        this.quizUserDataRepository = quizUserDataRepository;
        this.quizRepository = quizRepository;
        this.userDataService = userDataService;
    }


    @Transactional
    public QuizUserDataResponseDto getQuizUserData(User user, Long quizId) {

        QuizUserData quizUserData;
        //데이터가 없으면 생성, 있으면 조회
        if (!quizUserDataRepository.existsByUserAndQuiz_Id(user, quizId)) {
            Quiz foundQuiz = findQuizById(quizId);
            QuizUserData newQuizUserData = new QuizUserData(foundQuiz, user);
            quizUserData = quizUserDataRepository.save(newQuizUserData);
            //데이터가 없었다는 것은 처음 조회한것을 의미하므로.
            userDataService.countShowQuiz(user);
        } else {
            quizUserData = findQuizUserData(user, quizId);
        }
        if (quizUserData == null) throw new IllegalArgumentException("올바르지 않은 접근입니다.");


        return new QuizUserDataResponseDto(quizUserData);
    }

    @Transactional
    public void showHint(User user, Long quizId) {
        QuizUserData foundQuizUserData = findQuizUserData(user, quizId);
        foundQuizUserData.showHint();
        userDataService.countShowHint(user);
    }

    @Transactional
    public void solveQuiz(User user, Long quizId) {
        QuizUserData foundQuizUserData = findQuizUserData(user, quizId);
        foundQuizUserData.solveQuiz();
        userDataService.countSolveQuiz(user);
    }

    private QuizUserData findQuizUserData(User user, Long quizId) {
        return quizUserDataRepository.findByUserAndQuiz_Id(user, quizId).orElseThrow(() -> new NotFoundException("해당 퀴즈-유저 데이터가 없습니다"));
    }

    private Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElseThrow(() -> new IllegalArgumentException("해당 퀴즈가 존재하지 않습니다."));
    }
}
