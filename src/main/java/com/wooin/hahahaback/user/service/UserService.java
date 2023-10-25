package com.wooin.hahahaback.user.service;

import com.wooin.hahahaback.common.exception.NotFoundException;
import com.wooin.hahahaback.user.dto.SignupRequestDto;
import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.user.entity.UserRoleEnum;
import com.wooin.hahahaback.user.repository.UserRepository;
import com.wooin.hahahaback.userdata.service.UserDataService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDataService userDataService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    public void signup(SignupRequestDto requestDto, HttpServletResponse res){
        //Dto -> 변수에 담기
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword()); // 암호화해서 할당


        //회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            res.setStatus(400); //why 결국엔 redirect 되서 302로 덮어씌어짐.
            res.addHeader("message","existed username");
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
            log.info("role : "+role);
        }
        //Repo에 저장
        User user = User.builder()
                .username(username)
                .password(password)
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .role(role)
                .build();
        userRepository.save(user);
        userDataService.createData(user);
    }

    public User findUserById(Long id) {
        User foundUser = userRepository.findById(id).orElseThrow(()
                -> new NotFoundException("사용자를 찾을 수 없습니다."));
        return foundUser;
    }
}