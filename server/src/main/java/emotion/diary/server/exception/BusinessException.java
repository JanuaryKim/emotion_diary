package emotion.diary.server.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private int status;
    private String sentence = "";
    private String message;
    public BusinessException(ExceptionCode exceptionCode, String message) {
        this.sentence = exceptionCode.getSentence();
        this.status = exceptionCode.getCode();
        this.message = message;
    }
}
