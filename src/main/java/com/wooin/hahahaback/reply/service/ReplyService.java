package com.wooin.hahahaback.reply.service;

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
                .build();

        Reply savedReply = replyRepository.save(createdReply);

        return new ReplyResponseDto(savedReply);
    }
}
