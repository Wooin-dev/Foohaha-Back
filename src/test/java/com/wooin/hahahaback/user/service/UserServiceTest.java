package com.wooin.hahahaback.user.service;

import com.wooin.hahahaback.user.dto.SignupRequestDto;
import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.user.repository.UserRepository;
import com.wooin.hahahaback.userdata.service.UserDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserDataService userDataService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;


    @Test
    void 회원가입_성공() {
        ////Given
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .username("test")
                .password("1234qwer")
                .email("test@test.com")
                .nickname("testnick")
                .build();

        when(userRepository.findByUsername(requestDto.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPasswordExample");

        ////WHEN
        userService.signup(requestDto);

        ////THEN
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User savedUser = userArgumentCaptor.getValue();
        assertEquals(requestDto.getUsername(), savedUser.getUsername());
        assertEquals("encodedPasswordExample", savedUser.getPassword());
        assertNotEquals("1234qwer", savedUser.getPassword());
        assertEquals(requestDto.getNickname(), savedUser.getNickname());
    }


}