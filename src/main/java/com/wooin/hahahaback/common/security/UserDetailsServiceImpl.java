package com.wooin.hahahaback.common.security;


import com.wooin.hahahaback.user.entity.User;
import com.wooin.hahahaback.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j(topic = "userDetails 인증정보 가져오기")
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        long startTime = System.currentTimeMillis();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

        long endTime = System.currentTimeMillis();
        log.info(String.valueOf(endTime - startTime));
        return new UserDetailsImpl(user);
    }
}