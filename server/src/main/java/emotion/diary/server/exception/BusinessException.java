package emotion.diary.server.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private int status;
    private String message = "";
    public BusinessException(ExceptionCode exceptionCode) {
        this.message = exceptionCode.getMessage();
        this.status = exceptionCode.getCode();
    }
}
