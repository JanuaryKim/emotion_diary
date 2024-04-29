package emotion.diary.server.exception;

import lombok.Getter;

@Getter
public class AuthorityException extends RuntimeException{

    private int status;
    private String sentence = "";
    private String message;
    public AuthorityException(ExceptionCode exceptionCode, String message) {
        this.status = exceptionCode.getCode();
        this.sentence = exceptionCode.getSentence();
        this.message = message;
    }
}
