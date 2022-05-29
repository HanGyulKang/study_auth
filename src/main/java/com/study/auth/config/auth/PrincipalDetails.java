package com.study.auth.config.auth;


import com.study.auth.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료 되면 Session을 만들어준다.

// 다만 시큐리티를 사용할 시 Security ContextHolder(key값)에 넣어 준다.
// 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨
// User 오브젝트의 타입 = > UserDetails 타입 객체

// 시큐리티 세션 영역(Security Session) => Authentication 객체만 들어 감
// => Authentication 객체 안의 UserDetails 타입 객체에 User 정보가 있음
public class PrincipalDetails implements UserDetails {
    private User user; // 콤포지션

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호 사용 기간 체크
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정 활성화 여부
        // 우리 사이트 1년 로그인 안할 시 휴면 계정
        // 현재 시간 - 로그인 날짜 > 1년
        return true;
    }
}
