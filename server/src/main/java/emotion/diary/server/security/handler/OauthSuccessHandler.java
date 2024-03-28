package emotion.diary.server.security.handler;

import emotion.diary.server.jwt.JwtTokenizer;
import emotion.diary.server.member.repository.MemberRepository;
import emotion.diary.server.security.oauth2.CustomOauth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository; //!대체
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Oauth 인증 성공!");
        CustomOauth2User customOauth2User = (CustomOauth2User) authentication.getPrincipal();
        String memberId = customOauth2User.getOAuthAttributes().getMemberId();

//        Optional<Member> member = memberRepository.findBy
    }

    private void mainRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException{


//        String accessToken = jwtTokenizer.generateAccessToken();
//
//        if (refreshTokenRepository.findByMember(member).isPresent()) {
//            log.info("delete token");
//            tokenService.deleteToken(member.getMemberId());
//        }
//        String refreshToken = delegateRefreshToken(member);
//        String mainUri = createMainPageURI(accessToken, refreshToken).toString();
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setHeader("memberId", member.getMemberId());
//        response.setHeader("memberEmail", member.getMemberEmail());
//        getRedirectStrategy().sendRedirect(request, response, mainUri);
    }
}
