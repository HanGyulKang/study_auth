package com.study.auth.config.oauth;


import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    /**
     * 구글로부터 받은 userRequest 데이터에 대한 Oauth2 후처리
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(this.getClass().getName() + " =========================================");
        //registrationId로 어떤 oauth로 로그인했는지 확인 가능
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());

        /**
         * 1. 구글 로그인 버튼 클릭
         * 2. 구글 로그인창
         * 3. 로그인 완료
         * 4. code를 return받음(Oauth-Client 라이브러리)
         * 5. AccessToken 요청
         * 6. userRequest 정보
         * 7. loadUser 함수 실행
         * 8. 구글 회원프로필 정보 받음
         * [ 결론 ] loadUser 함수는 회원프로필 정보를 받는 녀석
          */
        System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return super.loadUser(userRequest);
    }
}
