package com.study.auth.controller;

import com.study.auth.dto.UserDto;
import com.study.auth.entity.User;
import com.study.auth.repository.UserRepository;
import com.study.auth.setEnum.UserRole;
import com.study.auth.setEnum.UserSocialType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.study.auth.setEnum.UserRole.*;
import static com.study.auth.setEnum.UserSocialType.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public String join(@RequestBody UserDto userDto) {
        logger.info(">>> Check User Data : " + userDto.toString());

        // 패스워드를 암호화 해야 로그인 가능(스프링 시큐리티)
        String rawPassword = userDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encPassword);
        user.setEmail(userDto.getEmail());
        user.setRole(ROLE_USER);
        user.setSocialType(KAKAO);
        userRepository.save(user);

        return userDto.getUsername();
    }

    @PostMapping("/login")
    public Mono<String> login() {
        return Mono.just("login");
    }
}
