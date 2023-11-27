package com.wooin.hahahaback.user.repository;

import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.user.entity.UserRoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트를 위한 임시 데이터 베이스를 생성하지 않음
class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    void saveNewUser() {
        ////Given
        User newUser = new User("test", "1234asdf", "test@test.com", "nickname", UserRoleEnum.USER);

        ////WHEN
        User savedTestUser = userRepository.save(newUser);

        ////THEN
        User testUser = userRepository.findByUsername("test").orElse(null);

        assertThat(testUser).isNotNull();

        assertThat(testUser.getUsername()).isEqualTo(savedTestUser.getUsername());
        assertThat(testUser)
                .extracting("password","email","nickname")
                .contains("1234asdf", "test@test.com", "nickname");
    }
}