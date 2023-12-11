package com.wooin.hahahaback.reply.service;

import com.wooin.hahahaback.common.exception.NoAuthorizedException;
import com.wooin.hahahaback.common.exception.NotFoundException;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.quiz.repository.QuizRepository;
import com.wooin.hahahaback.reply.dto.ReplyRequestDto;
import com.wooin.hahahaback.reply.dto.ReplyResponseDto;
import com.wooin.hahahaback.reply.entity.Reply;
import com.wooin.hahahaback.reply.repository.ReplyRepository;
import com.wooin.hahahaback.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "ReplyService")
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final QuizRepository quizRepository;

    public ReplyService(ReplyRepository replyRepository,
                        QuizRepository quizRepository) {
        this.replyRepository = replyRepository;
        this.quizRepository = quizRepository;
    }


    @Transactional
    public ReplyResponseDto createReply(User user, ReplyRequestDto requestDto, Long quizId) {

        Quiz gotQuiz = quizRepository.getReferenceById(quizId);

        //todo 빌더를 활용해서 생성자를 필요한곳에 바로 쓰는 것이 맞는가?
        // Reply 멤버가 바뀌면 여기저기 다 바꿔줘야하지 않나?
        Reply createdReply = Reply.builder()
                .contents(requestDto.getContents())
                .user(user)
                .quiz(gotQuiz)
                .likesCnt(0)
                .build();

        Reply savedReply = replyRepository.save(createdReply);

        return new ReplyResponseDto(savedReply);
    }

    @Transactional
    public ReplyResponseDto modifyReply(User user, ReplyRequestDto requestDto, Long replyId) {

        Reply foundReply = findReplyById(replyId);
        checkUserAuthorization(user, foundReply);

        foundReply.modifyReply(requestDto);

        return new ReplyResponseDto(foundReply);
    }

    @Transactional
    public void deleteReply(User user, Long replyId) {

        Reply foundReply = findReplyById(replyId);
        checkUserAuthorization(user, foundReply);

        replyRepository.delete(foundReply);
    }




    private Reply findReplyById(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(()-> new NotFoundException("해당 댓글을 찾을 수 없습니다."));
    }

    private void checkUserAuthorization(User user, Reply foundReply) {
        if (!user.getId().equals(foundReply.getUser().getId())) {
            throw new NoAuthorizedException("권한이 없습니다.");
        }
    }

}
