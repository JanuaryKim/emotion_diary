package emotion.diary.server.security.filter;

import emotion.diary.server.exception.AuthorityException;
import emotion.diary.server.exception.ErrorResponse;
import emotion.diary.server.exception.ExceptionCode;
import emotion.diary.server.jwt.JwtTokenizer;
import emotion.diary.server.util.Serializer;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtVerificationFilter extends OncePerRequestFilter {

    public JwtVerificationFilter(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    private final JwtTokenizer jwtTokenizer;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jws = request.getHeader("Authorization");

        if(jws != null) {
            try {
                Map<String, Object> claims = jwtTokenizer.getClaims(jws, jwtTokenizer.getSecretKey()).getBody();
//            verifyLogoutToken(jws); 로그아웃한 토큰인지 체크
                setAuthenticationToContext(claims);
            } catch (SignatureException se) {
                System.out.println(se.getMessage());
                handleAuthorityException(response, new AuthorityException(ExceptionCode.WRONG_SIGNATURE_JWT_TOKEN,se.getMessage()));
                return;
            } catch (ExpiredJwtException ee) {
                System.out.println(ee.getMessage());
                handleAuthorityException(response, new AuthorityException(ExceptionCode.EXPIRED_JWT_TOKEN, ee.getMessage()));
                return;
            } catch (MalformedJwtException me) {
                System.out.println(me.getMessage());
                handleAuthorityException(response, new AuthorityException(ExceptionCode.MALFORMED_JWT_TOKEN, me.getMessage()));
                return;
            } catch (UnsupportedJwtException ue) {
                System.out.println(ue.getMessage());
                handleAuthorityException(response, new AuthorityException(ExceptionCode.NOT_SUPPORTED_JWT_TOKEN, ue.getMessage()));
                return;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                handleAuthorityException(response, new AuthorityException(ExceptionCode.EXCEPTION_FROM_SERVER, ExceptionCode.EXCEPTION_FROM_SERVER.getSentence()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void handleAuthorityException(HttpServletResponse  response, AuthorityException authorityException) throws IOException {

        ErrorResponse errorResponse = ErrorResponse.of(authorityException.getStatus(), authorityException.getSentence());
        response.setStatus(authorityException.getStatus());
        String json = Serializer.toJson(errorResponse);
        response.setContentType("application/json; charset=UTF-8"); //브라우저에게 json 형태이며, utf-8 형식으로 인코딩 되어 있음을 알림
        response.getWriter().write(json);
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String id = (String) claims.get("memberId");
        List<String> roles = (List<String>) claims.get("memberRoles");
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(id, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
