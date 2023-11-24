package com.wooin.hahahaback.user.entity;

import com.wooin.hahahaback.common.entity.Timestamped;
import com.wooin.hahahaback.mypage.dto.EditMyProfileRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    ////컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private Long kakaoId;


    ////생성자
    @Builder
    public User(String username, String password, String email, String nickname, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    public User(String username, String password, String email, String nickname, UserRoleEnum role, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.kakaoId = kakaoId;
    }

    ////서비스 메소드
    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void editUser(EditMyProfileRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
    }


    ////연관관계
//
//    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true) //내가 등록한 퀴즈들.
//    private List<Quiz> quizzes = new ArrayList<>();


}