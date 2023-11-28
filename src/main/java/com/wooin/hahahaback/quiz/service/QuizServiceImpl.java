package com.wooin.hahahaback.quiz.service;

import com.wooin.hahahaback.common.exception.NoAuthorizedException;
import com.wooin.hahahaback.common.exception.NotFoundException;
import com.wooin.hahahaback.quiz.dto.QuizRequestDto;
import com.wooin.hahahaback.quiz.dto.QuizResponseDto;
import com.wooin.hahahaback.quiz.dto.QuizThumbResponseDto;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.quiz.repository.QuizRepository;
import com.wooin.hahahaback.reply.repository.ReplyRepository;
import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.user.repository.UserRepository;
import com.wooin.hahahaback.userdata.repository.UserDataRepository;
import com.wooin.hahahaback.userdata.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final UserDataService userDataService;
    private final UserDataRepository userDataRepository;
    private final ReplyRepository replyRepository;

    ////////////
    ////CRUD////
    ////////////
    @Override
    @Transactional
    public QuizResponseDto createQuiz(User user, QuizRequestDto requestDto) {

        Quiz newQuiz = new Quiz(requestDto, user);
        Quiz savedQuiz = quizRepository.save(newQuiz);

        userDataService.findUserDataByUser(user).countCreateQuiz();

        return new QuizResponseDto(savedQuiz);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "Quiz", key = "#quizId", unless = "#result==null")
    public QuizResponseDto selectOneQuiz(Long quizId) {
        return new QuizResponseDto(findQuizById(quizId));
    }


    @Override
    @Transactional(readOnly = true)
    public Page<QuizThumbResponseDto> selectQuizPage(int page, int size, String sortBy, boolean isAsc) {

        Pageable pageable = createPageable(page, size, sortBy, isAsc);
        Page<Quiz> quizzes = quizRepository.findAll(pageable);

        return getQuizThumbResponseDtoPage(quizzes);
    }

    public Integer countReplies(Quiz quiz) {
        return replyRepository.countByQuiz(quiz);
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
        userDataService.findUserDataByUser(user).discountCreateQuiz();

        quizRepository.deleteById(quizId);
    }


    ////////////
    ///MyPage///
    ////////////
    @Transactional(readOnly = true)
    public Page<QuizThumbResponseDto> selectMyCreatedQuizzes(User user, int page, int size, String sortBy, boolean isAsc) {
        Pageable pageable = createPageable(page, size, sortBy, isAsc);
        Page<Quiz> quizzes = quizRepository.findAllByUser(user, pageable);
        return getQuizThumbResponseDtoPage(quizzes);
    }

    @Transactional(readOnly = true)
    public Page<QuizThumbResponseDto> selectMyLikedQuizzes(User user, int page, int size, String sortBy, boolean isAsc) {
        Pageable pageable = createPageable(page, size, sortBy, isAsc);
        Page<Quiz> quizzes = quizRepository.findAllByQuizLikes_User(user, pageable);
        return getQuizThumbResponseDtoPage(quizzes);
    }

    @Transactional(readOnly = true)
    public Page<QuizThumbResponseDto> selectMySolvedQuizzes(User user, int page, int size, String sortBy, boolean isAsc) {
        Pageable pageable = createPageable(page, size, sortBy, isAsc);
        Page<Quiz> quizzes = quizRepository.findByQuizUserDatas_IsSolvedTrueAndQuizUserDatas_User(user, pageable);
        return getQuizThumbResponseDtoPage(quizzes);
    }

    @Transactional(readOnly = true)
    public Page<QuizThumbResponseDto> selectMyTryQuizzes(User user, int page, int size, String sortBy, boolean isAsc) {
        Pageable pageable = createPageable(page, size, sortBy, isAsc);
        Page<Quiz> quizzes = quizRepository.findByQuizUserDatas_UserAndQuizUserDatasNotEmptyAndQuizUserDatas_IsSolvedFalse(user, pageable);
        return getQuizThumbResponseDtoPage(quizzes);
    }

    @Transactional(readOnly = true)
    public Page<QuizThumbResponseDto> selectMyCheckedHintQuizzes(User user, int page, int size, String sortBy, boolean isAsc) {
        Pageable pageable = createPageable(page, size, sortBy, isAsc);
        Page<Quiz> quizzes = quizRepository.findByQuizUserDatas_UserAndQuizUserDatas_IsShowHintTrueAndQuizUserDatas_IsSolvedFalse(user, pageable);
        return getQuizThumbResponseDtoPage(quizzes);
    }

    //// Private 메소드
    @Transactional(readOnly = true)
    public Quiz findQuizById(Long quizId) {
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

    //Pagination
    private Pageable createPageable(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }

    private Page<QuizThumbResponseDto> getQuizThumbResponseDtoPage(Page<Quiz> quizzes) {
        return quizzes.map(quiz -> {
            Integer countReplies = countReplies(quiz);
            var dto = new QuizThumbResponseDto(quiz);
            dto.setCntReplies(countReplies);
            return dto;
        });
    }
}
