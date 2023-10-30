package com.wooin.hahahaback.mypage.service;

import com.wooin.hahahaback.common.exception.NotFoundException;
import com.wooin.hahahaback.mypage.dto.EditMyProfileRequestDto;
import com.wooin.hahahaback.mypage.dto.MyOverviewResponseDto;
import com.wooin.hahahaback.mypage.dto.MyProfileResponseDto;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.quiz.service.QuizServiceImpl;
import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.user.repository.UserRepository;
import com.wooin.hahahaback.user.service.UserService;
import com.wooin.hahahaback.userdata.entity.UserData;
import com.wooin.hahahaback.userdata.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserDataService userDataService;
    private final QuizServiceImpl quizService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MyOverviewResponseDto getMyOverview(User user) {

        UserData foundUserData = userDataService.findUserDataByUser(user);

        List<Quiz> foundQuizzes = quizService.selectMyQuizzes(user);

        return new MyOverviewResponseDto(foundUserData, foundQuizzes);
    }

    @Transactional(readOnly = true)
    public MyProfileResponseDto getMyProfile(User user) {

        UserData userData = userDataService.findUserDataByUser(user);
        return new MyProfileResponseDto(user, userData);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void editMyProfile(User user, EditMyProfileRequestDto requestDto) {

        userRepository.findById(user.getId()).orElseThrow(()
                -> new NotFoundException("사용자를 찾을 수 없습니다."))
                .editUser(requestDto);

    }
}
