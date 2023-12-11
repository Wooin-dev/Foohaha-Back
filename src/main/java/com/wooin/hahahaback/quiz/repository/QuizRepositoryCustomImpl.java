package com.wooin.hahahaback.quiz.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wooin.hahahaback.quiz.entity.QQuiz;
import com.wooin.hahahaback.quiz.entity.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryCustomImpl implements QuizRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    QQuiz quiz = QQuiz.quiz;

    @Override
    public Page<Quiz> searchQuizzes(Pageable pageable, String author, String question) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(pageable);

        List<Quiz> results = jpaQueryFactory
                .select(quiz)
                .from(quiz)
                .where(
                        eqAuthor(author),
                        containsQuestion(question)
                )
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(quiz.count())
                .from(quiz)
                .where(
                        eqAuthor(author),
                        containsQuestion(question)
                )
                .fetchOne();

        assert count != null;
        return new PageImpl<>(results, pageable, count);

    }


    private OrderSpecifier[] createOrderSpecifier(Pageable pageable) {

        if (pageable.getSort().isEmpty()) {
            return new OrderSpecifier[]{new OrderSpecifier(Order.DESC, quiz.createdAt)};
        }

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {

            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            String sortBy = order.getProperty();
            if (sortBy.equals("createdAt")) {
                orderSpecifiers.add(new OrderSpecifier(direction, quiz.createdAt));
            } else if (sortBy.equals("likes")) {
                orderSpecifiers.add(new OrderSpecifier(direction, quiz.likeCount));
            }
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }


    private BooleanExpression containsQuestion(String question) {
        return question.isEmpty() ? null : quiz.question.contains(question);
    }

    private BooleanExpression eqAuthor(String author) {
        return author.isEmpty() ? null : quiz.user.nickname.eq(author);
    }
}
