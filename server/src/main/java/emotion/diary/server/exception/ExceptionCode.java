package emotion.diary.server.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    NOT_EXISTS_DIARY(404, "존재하지 않는 일기");

    private int code;
    private String message;
    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
