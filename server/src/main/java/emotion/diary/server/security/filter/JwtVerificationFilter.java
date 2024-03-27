//package emotion.diary.server.security.filter;
//
//import emotion.diary.server.jwt.JwtTokenizer;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.security.SignatureException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class JwtVerificationFilter extends OncePerRequestFilter {
//
//    private final JwtTokenizer jwtTokenizer;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            String jws = request.getHeader("Authorization");
//
//            Map<String, Object> claims = verifyJws(jws);
//            verifyLogoutToken(jws);
//            setAuthenticationToContext(claims);
//
//        } catch (SignatureException se) {
//            log.error("SignatureException");
//            throw new AuthException(ExceptionCode.NOT_VALID_SIGNATURE);
//        } catch (ExpiredJwtException ee) {
//            log.error("ExpiredJwtException");
//            throw new AuthException(ExceptionCode.EXPIRED_JWT_TOKEN);
//        } catch (MalformedJwtException me) {
//            log.error("MalformedJwtException");
//            throw new AuthException(ExceptionCode.MALFORMED_JWT_EXCEPTION);
//        } catch (UnsupportedJwtException ue) {
//            log.error("UnsupportedJwtException");
//            throw new AuthException(ExceptionCode.UNSUPPORTED_JWT_EXCEPTION);
//        } catch (RuntimeException e) {
//            throw new AuthException(e.getMessage());
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
