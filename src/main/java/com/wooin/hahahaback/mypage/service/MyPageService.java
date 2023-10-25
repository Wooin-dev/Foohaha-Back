package com.wooin.hahahaback.mypage.service;

import com.wooin.hahahaback.mypage.dto.MyOverviewResponseDto;
import com.wooin.hahahaback.quiz.entity.Quiz;
import com.wooin.hahahaback.quiz.service.QuizServiceImpl;
import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.userdata.entity.UserData;
import com.wooin.hahahaback.userdata.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserDataService userDataService;
    private final QuizServiceImpl quizService;

    public MyOverviewResponseDto getMyOverview(User user) {

        UserData foundUserData = userDataService.findUserDataByUser(user);

        List<Quiz> foundQuizzes = quizService.selectMyQuizzes(user);

        return new MyOverviewResponseDto(foundUserData, foundQuizzes);
    }
}
