package com.study.auth.config;

import com.study.auth.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터 체인에 등록이 됨
@EnableGlobalMethodSecurity(securedEnabled = true // secured 어노테이션 활성화
                            , prePostEnabled = true) // preAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginPage("/loginForm")
                    .loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 해줌(login용도의 controller method를 만들 필요가 없음)
                    .defaultSuccessUrl("/")
                .and()
                    .oauth2Login()
                    .loginPage("/loginForm") // 구글 로그인이 완료된 뒤의 후처리 필요.
                    /**
                     * [ 후처리 ]
                     * 1. 코드 받기(사용자 인증)
                     * 2. 엑세스 토큰 발급(사용자 데이터 접근 권한)
                     * 3. 사용자 프로필 정보 가져오기
                     * 4-1. 받은 정보를 통해서 회원가입 자동 진행
                     * 4-2. 정보가 조금 부족하면 회원가입 수동 진행
                     */
                    .userInfoEndpoint()
                    .userService(principalOauth2UserService);
    }
}
