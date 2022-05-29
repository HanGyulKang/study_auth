package com.study.auth.controller;

import com.study.auth.dto.UserDto;
import com.study.auth.entity.User;
import com.study.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.study.auth.setEnum.UserRole.ROLE_USER;
import static com.study.auth.setEnum.UserSocialType.KAKAO;

@Controller
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /** =====================================================
     * [TEST]
     ===================================================== */
    @GetMapping("/index")
    public String index() {
        return "index";
    }


    /** =====================================================
     * [ROLE URL]
     ===================================================== */
    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }


    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(UserDto userDto) throws Exception {
        logger.info(">>> Check User Data : " + userDto.toString());

        User byUsername = userRepository.findByUsername(userDto.getUsername());
        if(byUsername != null) {
            throw new Exception("중복된 아이디입니다.");
        }

        // 패스워드를 암호화 해야 로그인 가능(스프링 시큐리티)
        String rawPassword = userDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encPassword);
        user.setEmail(userDto.getEmail());
        user.setRole(ROLE_USER);
        user.setSocialType(KAKAO);
        user.setDeleted(false);

        userRepository.save(user);

        return "redirect:/loginForm";
    }

}
