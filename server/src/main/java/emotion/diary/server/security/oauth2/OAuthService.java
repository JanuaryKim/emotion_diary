package emotion.diary.server.security.oauth2;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    //oauth 로그인의 경우 이미 타사이트로 부터 인증을 받았기 때문에, oauth 인증이 완료 된 경우
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //본 OauthService 클래스를 만들어 빈을 갈아끼우지 않았다면 원래 빈으로써 작동할 원래 DefaultOAuth2UserService 객체를 생성하여
        //본 메소드의 원래 작동을 하는 loadUser에 들어온 request를 전달하여 기본적으로 만들어지는 OAuth2User 객체를 받아
        //본 메소드에서 필요에 맞게 재 가공하여 최종적으로 CustomOauth2User 객체로 전달함.
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 공통, 플랫폼별 id, 해당 로그인 유저가 어떤 플랫폼으로 로그인을 한 것인지 알기 위해
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        return new CustomOauth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }
}
