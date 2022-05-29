package com.study.auth.config.oauth;


import com.study.auth.config.auth.PrincipalDetails;
import com.study.auth.entity.User;
import com.study.auth.repository.UserRepository;
import com.study.auth.setEnum.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    /**
     * 구글로부터 받은 userRequest 데이터에 대한 Oauth2 후처리
     */
    // 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다.
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


        /**
         * 강제 회원 가입
         */
        String socialType = userRequest.getClientRegistration().getRegistrationId(); // 소셜 종류(google, kakao ...)
        String socialId = oAuth2User.getAttribute("sub");
        String username = socialType + "_" + socialId; // 예 : google_123450192495810190293
        String password = bCryptPasswordEncoder.encode(socialType); // 어차피 필요 없음
        String email = oAuth2User.getAttribute("email");
        String role = UserRole.ROLE_USER.getRole();

        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
            System.out.println("최초 구글 로그인");
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .socialType(socialType)
                    .socialId(socialId)
                    .build();

            userRepository.save(userEntity);
        } else {
            System.out.println("이미 로그인 한 회원[가입 되어있음]");
        }

        // 한 번에 두 가지 해결(일반 가입, 소셜 가입)
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
