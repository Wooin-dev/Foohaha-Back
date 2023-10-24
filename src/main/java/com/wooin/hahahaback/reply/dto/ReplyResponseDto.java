package com.wooin.hahahaback.reply.dto;

import com.wooin.hahahaback.reply.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyResponseDto {

    private Long id;
    private String contents;
    private String author;
    private LocalDateTime createdAt;

    @Builder
    public ReplyResponseDto(Long id, String contents, String author, LocalDateTime createdAt) {
        this.id = id;
        this.contents = contents;
        this.author = author;
        this.createdAt = createdAt;
    }


    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.contents = reply.getContents();
        this.author = reply.getUser().getNickname();
        this.createdAt = reply.getCreatedAt();
    }
}
