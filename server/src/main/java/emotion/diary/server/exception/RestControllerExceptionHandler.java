package emotion.diary.server.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(value
            = { BusinessException.class, BusinessException.class })
    protected ErrorResponse handleBusinessException(BusinessException exception){

        return ErrorResponse.of(exception.getStatus(), exception.getSentence());
    }

    @ExceptionHandler(value
            = { AuthorityException.class, AuthorityException.class })
    protected ErrorResponse handleAuthorityException(AuthorityException exception){

        return ErrorResponse.of(exception.getStatus(), exception.getSentence());
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        return ErrorResponse.of(401, "DTO의 필드 유효성 검증 에러", exception.getBindingResult());
    }
}
