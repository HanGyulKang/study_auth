package com.study.auth.controller;

import com.study.auth.config.auth.PrincipalDetails;
import com.study.auth.dto.UserDto;
import com.study.auth.entity.User;
import com.study.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.study.auth.setEnum.UserRole.*;
import static com.study.auth.setEnum.UserSocialType.GOOGLE;
import static com.study.auth.setEnum.UserSocialType.KAKAO;

@Controller
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    /** =====================================================
     * [세션 정보 확인 TEST]
     * 1. 일반 세션 안에 spring security만이 사용하는 session이 들어있다.
     * 2. 시큐리티 세션에는 Authentication 타입의 데이터만 들어간다.
     * 3. Authentication 타입 안에는 UserDetails와 OAuth2User 객체만 넣을 수 있다.
     *
     * [결론]
     * 시큐리티 세션 <= Authentication 객체 <= UserDetails 또는 OAuth2User 객체
     *      - UserDetails 객체 : 일반 유저 로그인 시 생성
     *      - OAuth2User 객체 : 소셜 로그인 시 생성
     ===================================================== */
    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication,
                            // 세션 정보에 접근 가능
                            @AuthenticationPrincipal UserDetails userDtails) { // DI(의존성주입)

        System.out.println("[ 1 - 세션 정보 접근 ] =============== authentication.getPrincipal() ================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication : " + principalDetails.toString());

        System.out.println("[ 2 - 세션 정보 접근 ] ================ userDtails ===============");
        System.out.println("userDtails[username] : " + userDtails.getUsername());
        System.out.println("userDtails[toString] : " + userDtails.toString());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication,
                        @AuthenticationPrincipal OAuth2User oauth) {

        System.out.println("[ OAuth - 세션 정보 접근 ] =============== authentication.getPrincipal() ================");
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication : " + oauth2User.getAttributes());

        System.out.println("[ OAuth - 세션 정보 접근 ] ================ userDtails ===============");
        System.out.println("userDtails[username] : " + oauth.getAttributes());
        return "OAuth 세션 정보 확인하기";
    }



    /** =====================================================
     * [TEST]
     ===================================================== */
    @GetMapping({"","/index"})
    public String index() {
        return "index";
    }


    /** =====================================================
     * [ROLE URL]
     ===================================================== */
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 일반 유저 로그인, 소셜 로그인 동시에 PrincipalDetails로 받을 수 있음
        System.out.println("principalDetails : " + principalDetails);
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
            user.setRole(ROLE_MANAGER.getRole());
        } else if("admin".equals(userDto.getUsername())) {
            user.setRole(ROLE_ADMIN.getRole());
        } else {
            user.setRole(ROLE_USER.getRole());
        }

        user.setSocialType(GOOGLE.getSocialType());
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
