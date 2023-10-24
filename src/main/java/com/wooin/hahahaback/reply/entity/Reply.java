package com.wooin.hahahaback.reply.entity;

import com.wooin.hahahaback.common.entity.Timestamped;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.reply.dto.ReplyRequestDto;
import com.wooin.hahahaback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reply extends Timestamped {


    ////컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contents;


    ////연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;


    public void modifyReply(ReplyRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
