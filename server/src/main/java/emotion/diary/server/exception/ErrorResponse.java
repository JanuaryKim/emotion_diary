package emotion.diary.server.exception;

public class ErrorResponse {

    int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse of(ExceptionCode exceptionCode){

        return new ErrorResponse(exceptionCode.getCode(), exceptionCode.getMessage());
    }
}
