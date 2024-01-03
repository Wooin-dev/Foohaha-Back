package com.wooin.hahahaback.mypage.repository;

import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.quiz.repository.QuizRepository;
import com.wooin.hahahaback.quiz.service.QuizService;
import com.wooin.hahahaback.quizUserData.service.QuizUserDataService;
import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.user.entity.UserRoleEnum;
import com.wooin.hahahaback.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class MyPageRepositoryIntTest {

    @Autowired
    QuizService quizService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuizUserDataService quizUserDataService;

    private User testUser;

    @BeforeEach //refactor BeforeAll처럼 작동하게 하고 싶다. 최적화 필요
    void setUp() {

        User quizCreatorUser = User.builder()
                .username("quizCreatorUser")
                .password("qwer1234")
                .email("test@test.com")
                .nickname("quizCreatorUser")
                .role(UserRoleEnum.USER)
                .build();
        User savedQuizCreatorUser = userRepository.save(quizCreatorUser);

        Quiz quiz1 = quizRepository.save(Quiz.builder().question("question test1").answer("answer test1").user(savedQuizCreatorUser).build());
        Quiz quiz2 = quizRepository.save(Quiz.builder().question("question test2").answer("answer test2").user(savedQuizCreatorUser).build());
        Quiz quiz3 = quizRepository.save(Quiz.builder().question("question test3").answer("answer test3").user(savedQuizCreatorUser).build());
        Quiz quiz4 = quizRepository.save(Quiz.builder().question("question test4").answer("answer test4").user(savedQuizCreatorUser).build());


        User testUser = User.builder()
                .username("testUser")
                .password("qwer1234")
                .email("test1@test.com")
                .nickname("testMan")
                .role(UserRoleEnum.USER)
                .build();
        User savedTestUser = userRepository.save(testUser);
        this.testUser = savedTestUser;

        //quiz1 -> 힌트열람 O 문제 풀이 O -> MySolvedQuiz
        quizUserDataService.getQuizUserData(savedTestUser, quiz1.getId());
        quizUserDataService.checkHint(savedTestUser, quiz1.getId());
        quizUserDataService.solveQuiz(savedTestUser, quiz1.getId());

        //quiz2 -> 힌트열람 X 문제 풀이 O -> MySolvedQuiz
        quizUserDataService.getQuizUserData(savedTestUser, quiz2.getId());
        quizUserDataService.solveQuiz(savedTestUser, quiz2.getId());

        //quiz3 -> 힌트열람 O 문제 풀이 X -> MyCheckedHintQuiz
        quizUserDataService.getQuizUserData(savedTestUser, quiz3.getId());
        quizUserDataService.checkHint(savedTestUser, quiz3.getId());

        //quiz4 -> 힌트열람 X 문제풀이 X -> MyTryQuiz
        quizUserDataService.getQuizUserData(savedTestUser, quiz4.getId());
    }

    @Test
    void selectMyCheckedHintQuizzes() {
        //when
        var page = quizService.selectMyCheckedHintQuizzes(testUser, 0, 10, "createdAt", false);

        //then
        assertThat(page.getTotalElements()).isEqualTo(1L);

        var result = page.getContent().get(0);
        assertThat(result.getQuestion()).isEqualTo("question test3");
    }

    @Test
    void selectMyTryQuizzes() {
        //when
        var page = quizService.selectMyTryQuizzes(testUser, 0, 10, "createdAt", false);

        //then
        assertThat(page.getTotalElements()).isEqualTo(2L);

        var result1 = page.getContent().get(0);
        assertThat(result1.getQuestion()).isEqualTo("question test3");
        var result2 = page.getContent().get(1);
        assertThat(result2.getQuestion()).isEqualTo("question test4");
    }

    @Test
    void selectMySolvedQuizzes() {
        //when
        var page = quizService.selectMySolvedQuizzes(testUser, 0, 10, "createdAt", false);

        //then
        assertThat(page.getTotalElements()).isEqualTo(2L);

        var result1 = page.getContent().get(0);
        assertThat(result1.getQuestion()).isEqualTo("question test1");
        var result2 = page.getContent().get(1);
        assertThat(result2.getQuestion()).isEqualTo("question test2");
    }
}