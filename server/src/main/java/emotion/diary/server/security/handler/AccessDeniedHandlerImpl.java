package emotion.diary.server.security.handler;

import emotion.diary.server.exception.ErrorResponse;
import emotion.diary.server.util.Serializer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("권한이 부족한 유저의 접근");
        ErrorResponse errorResponse = ErrorResponse.of(403, "접근할 수 없는 요청입니다");
        response.setStatus(403);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(Serializer.toJson(errorResponse));
    }
}
