package emotion.diary.server.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(value
            = { BusinessException.class, BusinessException.class })
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception){
        ErrorResponse errorResponse = ErrorResponse.of(exception.getStatus(), exception.getSentence());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(value
            = { AuthorityException.class, AuthorityException.class })
    protected ResponseEntity<ErrorResponse> handleAuthorityException(AuthorityException exception){
        ErrorResponse errorResponse = ErrorResponse.of(exception.getStatus(), exception.getSentence());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        ErrorResponse errorResponse = ErrorResponse.of(ExceptionCode.DTO_VALIDATION_FAILED.getCode(), ExceptionCode.DTO_VALIDATION_FAILED.getSentence(), exception.getBindingResult());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatus()));
    }

    //Max, Min, Length 와 같은 기존 Validation 어노테이션 검증 예외 발생시
    @ExceptionHandler(value = HandlerMethodValidationException.class)
    protected ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException exception){
        ErrorResponse errorResponse = ErrorResponse.of(ExceptionCode.DTO_VALIDATION_FAILED.getCode(), ExceptionCode.DTO_VALIDATION_FAILED.getSentence(),exception.getAllErrors());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class) //패스파라미터 타입 안 맞을때
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        ErrorResponse errorResponse = ErrorResponse.of(ExceptionCode.PATH_VARIABLE_TYPE_INCORRECT.getCode(), ExceptionCode.PATH_VARIABLE_TYPE_INCORRECT.getSentence(), (TypeMismatchException)exception);
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception){
        exception.printStackTrace();
        ErrorResponse errorResponse = ErrorResponse.of(ExceptionCode.EXCEPTION_FROM_SERVER.getCode(), ExceptionCode.EXCEPTION_FROM_SERVER.getSentence());
        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(errorResponse.getStatus()));
    }
}
