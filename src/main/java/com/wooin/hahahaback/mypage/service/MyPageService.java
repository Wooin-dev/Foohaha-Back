package com.wooin.hahahaback.mypage.service;

import com.wooin.hahahaback.common.exception.NotFoundException;
import com.wooin.hahahaback.mypage.dto.EditMyProfileRequestDto;
import com.wooin.hahahaback.mypage.dto.MyProfileResponseDto;
import com.wooin.hahahaback.quiz.repository.QuizRepository;
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

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserDataService userDataService;
    private final QuizServiceImpl quizService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

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
//
//
//    @Transactional(readOnly = true)
//    public Page<QuizThumbResponseDto> selectMyQuizzes(User user, int page, int size, String sortBy, boolean isAsc) {
//
//        //페이징 처리 // refactor 유틸클래스로 중복 줄일 수 있을 듯
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        Page<Quiz> quizzes = quizRepository.findAllByUser(user, pageable);
//        return quizzes
//                .map(quiz -> {
//                    Integer countReplies = countReplies(quiz);
//                    var dto = new QuizThumbResponseDto(quiz);
//                    dto.setCntReplies(countReplies);
//                    return dto;
//                });
//
//
//
//        return
//    }
//

}
