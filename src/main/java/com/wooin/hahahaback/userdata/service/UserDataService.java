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
        UserData foundUserData = userDataRepository.findByUser(user).orElse(null);

        if (foundUserData==null) {
            foundUserData = createData(user);
        }

        foundUserData.countShowQuiz();

        return foundUserData;
    }

    @Transactional
    public UserData showHintCnt(User user){
        UserData foundUserData = userDataRepository.findByUser(user).orElse(null);

        if (foundUserData==null) {
            foundUserData = createData(user);
        }

        foundUserData.countShowHint();

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
