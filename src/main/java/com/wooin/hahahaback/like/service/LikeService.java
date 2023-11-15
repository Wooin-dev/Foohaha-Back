package com.wooin.hahahaback.like.service;

import com.wooin.hahahaback.common.exception.NotFoundException;
import com.wooin.hahahaback.like.entity.QuizLike;
import com.wooin.hahahaback.like.entity.ReplyLike;
import com.wooin.hahahaback.like.repository.QuizLikeRepository;
import com.wooin.hahahaback.like.repository.ReplyLikeRepository;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.quiz.repository.QuizRepository;
import com.wooin.hahahaback.reply.entity.Reply;
import com.wooin.hahahaback.reply.repository.ReplyRepository;
import com.wooin.hahahaback.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    private final QuizLikeRepository quizLikeRepository;
    private final QuizRepository quizRepository;
    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;

    public LikeService(QuizLikeRepository quizLikeRepository,
                       QuizRepository quizRepository,
                       ReplyRepository replyRepository,
                       ReplyLikeRepository replyLikeRepository) {
        this.quizLikeRepository = quizLikeRepository;
        this.quizRepository = quizRepository;
        this.replyRepository = replyRepository;
        this.replyLikeRepository = replyLikeRepository;
    }

    @Transactional(readOnly = true)
    public Boolean isLikedQuiz(User user, Long quizId) {
        return quizLikeRepository.existsByUserAndQuiz_Id(user, quizId);
    }

    @Transactional
    public void likeQuiz(User user, Long quizId) {

        if (quizLikeRepository.existsByUserAndQuiz_Id(user, quizId)) {
            throw new IllegalArgumentException("이미 좋아요를 한 상태입니다.");
        }

        Quiz foundQuiz = findQuizById(quizId);

        foundQuiz.hitLikeCount();

        QuizLike newQuizLike = QuizLike.builder()
                .quiz(foundQuiz)
                .user(user)
                .build();

        quizLikeRepository.save(newQuizLike);
    }



    @Transactional
    public void likeCancleQuiz(User user, Long quizId) {

        if (!quizLikeRepository.existsByUserAndQuiz_Id(user, quizId)) {
            throw new IllegalArgumentException("아직 좋아요를 누르지 않았습니다.");
        }

        Quiz foundQuiz = findQuizById(quizId);
        foundQuiz.cancleLikeCount();

        quizLikeRepository.deleteByQuizAndUser(foundQuiz, user);
    }


    @Transactional
    public void likeReply(User user, Long replyId) {
        if (replyLikeRepository.existsByUserAndReply_Id(user, replyId)) {
            throw new IllegalArgumentException("이미 좋아요를 한 상태입니다.");
        }

        Reply foundReply = findReplyById(replyId);
        foundReply.hitLikeCount();

        ReplyLike newReplyLike = ReplyLike.builder()
                .reply(foundReply)
                .user(user)
                .build();

        replyLikeRepository.save(newReplyLike);
    }



    @Transactional
    public void likeCancleReply(User user, Long replyId) {
        if (!replyLikeRepository.existsByUserAndReply_Id(user, replyId)) {
            throw new IllegalArgumentException("아직 좋아요를 누르지 않았습니다.");
        }

        Reply foundReply = findReplyById(replyId);
        foundReply.cancleLikeCount();

        replyLikeRepository.deleteByUserAndReply(user, foundReply);
    }


    private Quiz findQuizById(Long quizId) {
        Quiz foundQuiz = quizRepository.findById(quizId)
                .orElseThrow(()-> new NotFoundException("퀴즈를 찾을 수 없습니다."));
        return foundQuiz;
    }

    private Reply findReplyById(Long replyId) {
        Reply foundReply = replyRepository.findById(replyId).orElseThrow(()-> new NotFoundException("댓글을 찾을 수 없습니다."));
        return foundReply;
    }
}
