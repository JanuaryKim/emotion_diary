package emotion.diary.server.security.handler;

import emotion.diary.server.exception.ErrorResponse;
import emotion.diary.server.util.Serializer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorResponse errorResponse = ErrorResponse.of(401, "해당 자원에 접근을 위해선 인증을 하여야 합니다");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(Serializer.toJson(errorResponse));
    }
}
