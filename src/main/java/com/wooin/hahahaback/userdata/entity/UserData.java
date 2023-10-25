package com.wooin.hahahaback.userdata.entity;

import com.wooin.hahahaback.common.entity.Timestamped;
import com.wooin.hahahaback.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserData extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer showQuiz;
    private Integer showHint;
    private Integer solveQuiz;

    private Integer createQuiz;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public UserData(User user) {
        this.showQuiz = 0;
        this.showHint = 0;
        this.solveQuiz = 0;
        this.createQuiz = 0;
        this.user = user;
    }

    public Integer countShowQuiz() {
        return this.showQuiz++;
    }

    public Integer countShowHint() {
        return this.showHint++;
    }

    public Integer countSolveQuiz() {
        return this.solveQuiz++;
    }

    public Integer countCreateQuiz() {
        return this.createQuiz++;
    }
}
