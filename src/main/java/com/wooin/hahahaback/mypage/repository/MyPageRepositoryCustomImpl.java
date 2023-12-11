package com.wooin.hahahaback.mypage.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wooin.hahahaback.quiz.entity.QQuiz;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.quizUserData.entity.QQuizUserData;
import com.wooin.hahahaback.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyPageRepositoryCustomImpl implements MyPageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QQuiz quiz = QQuiz.quiz;
    QQuizUserData quizUserData = QQuizUserData.quizUserData;

    @Override
    public Page<Quiz> selectMyCheckedHintQuizzes(User user, Pageable pageable) {

        return selectMyQuizzesWhereQuizUserData(pageable, eqMyCheckedHintQuiz(user));
    }

    @Override
    public Page<Quiz> selectMyTryQuizzes(User user, Pageable pageable) {

        return selectMyQuizzesWhereQuizUserData(pageable, eqMyTryQuiz(user));
    }

    @Override
    public Page<Quiz> selectMySolvedQuizzes(User user, Pageable pageable) {

        return selectMyQuizzesWhereQuizUserData(pageable, eqMySolvedQuiz(user));
    }


    private Page<Quiz> selectMyQuizzesWhereQuizUserData(Pageable pageable, BooleanExpression expression) {

        List<Quiz> results = jpaQueryFactory
                .select(quiz) // refactor select() 안에 Projection 객체를 넣어줄 수 있다
                .from(quiz)
                .join(quizUserData).on(quizUserData.quiz.eq(quiz))
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(quiz.count())
                .from(quiz)
                .join(quizUserData).on(quizUserData.quiz.eq(quiz))
                .where(expression)
                .fetchOne();

        assert count != null;
        return new PageImpl<>(results, pageable, count);
    }

    private BooleanExpression eqMySolvedQuiz(User user) {
        return quizUserData.user.eq(user)
                .and(quizUserData.isSolved.isTrue());
    }

    private BooleanExpression eqMyTryQuiz(User user) {
        return quizUserData.user.eq(user)
                .and(quizUserData.isNotNull())
                .and(quizUserData.isSolved.isFalse());
    }

    private BooleanExpression eqMyCheckedHintQuiz(User user) {
        return quizUserData.user.eq(user)
                .and(quizUserData.isCheckedHint.isTrue())
                .and(quizUserData.isSolved.isFalse());
    }
}
