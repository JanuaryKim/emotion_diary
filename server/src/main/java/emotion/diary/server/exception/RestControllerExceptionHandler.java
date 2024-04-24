package emotion.diary.server.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(value
            = { BusinessException.class, BusinessException.class })
    protected ErrorResponse handleBusinessException(BusinessException exception){

        return new ErrorResponse(exception.getStatus(), exception.getMessage());
    }
}
