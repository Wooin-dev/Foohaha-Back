package com.wooin.hahahaback.like.service;

import com.wooin.hahahaback.like.entity.QuizLike;
import com.wooin.hahahaback.like.repository.QuizLikeRepository;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.quiz.repository.QuizRepository;
import com.wooin.hahahaback.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    private final QuizLikeRepository quizLikeRepository;
    private final QuizRepository quizRepository;

    public LikeService(QuizLikeRepository quizLikeRepository,
                       QuizRepository quizRepository) {
        this.quizLikeRepository = quizLikeRepository;
        this.quizRepository = quizRepository;
    }

    @Transactional
    public void likeQuiz(User user, Long quizId) {

        if (quizLikeRepository.existsByQuiz_IdAndUser(quizId, user)) {
            throw new IllegalArgumentException("이미 좋아요를 한 상태입니다.");
        }

        Quiz foundQuiz = quizRepository.getReferenceById(quizId);

        QuizLike newQuizLike = QuizLike.builder()
                .quiz(foundQuiz)
                .user(user)
                .build();

        quizLikeRepository.save(newQuizLike);
    }

    @Transactional
    public void likeCancleQuiz(User user, Long quizId) {

        if (!quizLikeRepository.existsByQuiz_IdAndUser(quizId, user)) {
            throw new IllegalArgumentException("아직 좋아요를 누르지 않았습니다.");
        }

        Quiz foundQuiz = quizRepository.getReferenceById(quizId);

        quizLikeRepository.deleteByQuizAndUser(foundQuiz, user);
    }



}
