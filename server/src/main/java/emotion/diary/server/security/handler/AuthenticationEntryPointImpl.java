package emotion.diary.server.security.handler;

import emotion.diary.server.exception.ErrorResponse;
import emotion.diary.server.exception.ExceptionCode;
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
        ErrorResponse errorResponse = ErrorResponse.of(ExceptionCode.UNAUTHENTICATED_REQUEST.getCode(), ExceptionCode.UNAUTHENTICATED_REQUEST.getSentence());
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(errorResponse.getStatus());
        response.getWriter().write(Serializer.toJson(errorResponse));
    }
}
