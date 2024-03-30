package emotion.diary.server.security.handler;

import emotion.diary.server.jwt.JwtTokenizer;
import emotion.diary.server.member.entity.Member;
import emotion.diary.server.member.service.MemberService;
import emotion.diary.server.security.oauth2.CustomOauth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
//테스트 주석 추가
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Oauth 인증 성공!");
        CustomOauth2User customOauth2User = (CustomOauth2User) authentication.getPrincipal();
        String memberId = customOauth2User.getOAuthAttributes().getMemberId();
        String memberEmail = customOauth2User.getOAuthAttributes().getMemberEmail();
        String socialKind = customOauth2User.getOAuthAttributes().getSocialKind();

        Optional<Member> optionalMember = memberService.findMember(memberId);
        Member member;
        if(optionalMember.isPresent()){ //간편 로그인 했던 유저
            //멤버 정보 업데이트
            memberService.updateMember(memberId, memberEmail);
            member = optionalMember.get();
        }
        else {
            //신규 멤버 정보 등록
            member = memberService.createMember(memberId, memberEmail, socialKind);
        }
        String accessToken = delegateAccessToken(member.getEmail()); //액세스 토큰 발행
        URI redirectURI = createRedirectURI(accessToken); //리다이렉트 URI 생성
        redirect(request,response,accessToken, redirectURI.toString()); //리다이렉트 시킴
    }


    private String delegateAccessToken(String memberEmail) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("memberEmail", memberEmail);
        String subject = "accessToken";
        Date accessTokenExpirationDate = jwtTokenizer.getAccessTokenExpiration();
        return jwtTokenizer.generateAccessToken(claims, subject,accessTokenExpirationDate,jwtTokenizer.getSecretKey());
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String accessToken,String redirectUrl) throws IOException{
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("access_token", accessToken);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private URI createRedirectURI(String accessToken) {
//        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
//        queryParams.add("access_token", accessToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .path("/")
                .port(3000)
//                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
