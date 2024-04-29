package emotion.diary.server.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {

    int status;
    private String message;
    List<FieldErrorResponse> fieldErrorResponseList = new ArrayList<>();

    private ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private ErrorResponse(int status, String message, List<FieldErrorResponse> fieldErrorResponseList){
        this.status = status;
        this.message = message;
        this.fieldErrorResponseList = fieldErrorResponseList;
    }

    public static ErrorResponse of(int status, String message){
        return new ErrorResponse(status, message);
    }

    public static ErrorResponse of(int status, String message, BindingResult bindingResult){
        return new ErrorResponse(status, message, FieldErrorResponse.of(bindingResult));
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class FieldErrorResponse {

        String field;
        Object rejectedValue;
        String reason;

        public static List<FieldErrorResponse> of(BindingResult bindingResult){
            final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream()
                    .map(error -> FieldErrorResponse.builder()
                            .field(error.getField())
                            .rejectedValue(error.getRejectedValue() == null ? "" : error.getRejectedValue().toString())
                            .reason(error.getDefaultMessage())
                            .build())
                    .collect(Collectors.toList());
        }
    }
}
