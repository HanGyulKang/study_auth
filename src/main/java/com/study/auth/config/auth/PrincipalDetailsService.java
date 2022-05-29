package com.study.auth.config.auth;

import com.study.auth.entity.User;
import com.study.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Strping Security 설정에서 loginProcessingUrl("/login");
// login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 화면에서 username을 넘기는 Key 명칭과 아래 메소드의 변수 명이 동일해야 한다.
     * Security session <= Authentication <= UserDetails
     * [결과]
     * {
     *     "Security session" : {
     *         "Authentication" : {
     *             "UserDetails" : UserDetails
     *         }
     *     }
     * }
      */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user != null) {
            return new PrincipalDetails(user);
        }

        return null;
    }
}
