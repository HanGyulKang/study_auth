package com.study.auth.controller;

import com.study.auth.dto.UserDto;
import com.study.auth.entity.User;
import com.study.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.study.auth.setEnum.UserRole.*;
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
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
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

//        if("manager".equals(userDto.getUsername())) {
//            user.setRole(MANAGER);
//        } else if("admin".equals(userDto.getUsername())) {
//            user.setRole(ADMIN);
//        } else {
//            user.setRole(USER);
//        }
        if("manager".equals(userDto.getUsername())) {
            user.setRole("ROLE_MANAGER");
        } else if("admin".equals(userDto.getUsername())) {
            user.setRole("ROLE_ADMIN");
        } else {
            user.setRole("ROLE_USER");
        }

        user.setSocialType(KAKAO);
        user.setDeleted(false);

        userRepository.save(user);

        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN") // 특정 하나의 권한만 접근 가능
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }


    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 여러 권한 접근 가능
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터 정보";
    }

}
