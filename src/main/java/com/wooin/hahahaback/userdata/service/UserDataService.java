package com.wooin.hahahaback.userdata.service;

import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.userdata.entity.UserData;
import com.wooin.hahahaback.userdata.repository.UserDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDataService {

    private final UserDataRepository userDataRepository;

    public UserDataService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }


    @Transactional
    public UserData createData(User user) {

        UserData newUserData = new UserData(user);
        return userDataRepository.save(newUserData);
    }

    @Transactional
    public UserData countShowQuiz(User user){
        UserData foundUserData = findUserDataByUser(user);
        foundUserData.countShowQuiz();
        return foundUserData;
    }

    @Transactional
    public UserData countShowHint(User user){
        UserData foundUserData = findUserDataByUser(user);
        foundUserData.countShowHint();
        return foundUserData;
    }

    @Transactional
    public UserData countSolveQuiz(User user) {
        UserData foundUserData = findUserDataByUser(user);
        foundUserData.countSolveQuiz();
        return foundUserData;
    }


    @Transactional
    public UserData findUserDataByUser(User user) {

        UserData foundUserData = userDataRepository.findByUser(user).orElse(null);
        if (foundUserData==null) {
            foundUserData = userDataRepository.save(new UserData(user));
        }
        return foundUserData;
    }
}
