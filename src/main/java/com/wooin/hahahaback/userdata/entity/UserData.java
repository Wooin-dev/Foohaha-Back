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

    private Integer showQuizCnt;
    private Integer showHintCnt;
    private Integer solveQuizCnt;
    private Integer createQuizCnt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public UserData(User user) {
        this.showQuizCnt = 0;
        this.showHintCnt = 0;
        this.solveQuizCnt = 0;
        this.createQuizCnt = 0;
        this.user = user;
    }

    public Integer countShowQuiz() {
        return this.showQuizCnt++;
    }

    public Integer countShowHint() {
        return this.showHintCnt++;
    }

    public Integer countSolveQuiz() {
        return this.solveQuizCnt++;
    }

    public Integer countCreateQuiz() {
        return this.createQuizCnt++;
    }
    public Integer discountCreateQuiz() {
        return this.createQuizCnt--;
    }
}
