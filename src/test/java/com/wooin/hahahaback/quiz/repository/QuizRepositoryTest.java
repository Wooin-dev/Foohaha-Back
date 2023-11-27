package com.wooin.hahahaback.quiz.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트를 위한 임시 데이터 베이스를 생성하지 않음
class QuizRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    QuizRepository quizRepository;



    @Test
    void findAllByUser() {
        ////Given


        ////WHEN


        ////THEN


    }
}