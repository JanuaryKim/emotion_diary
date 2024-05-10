package emotion.diary.server.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {

    private int status;
    private String message;
    List<FieldErrorResponse> fieldErrorResponseList = new ArrayList<>();
    TypeMissResponse typeMissResponse;

    private ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private ErrorResponse(int status, String message, List<FieldErrorResponse> fieldErrorResponseList, TypeMissResponse typeMissResponse){
        this.status = status;
        this.message = message;
        this.fieldErrorResponseList = fieldErrorResponseList;
        this.typeMissResponse = typeMissResponse;
    }

    public static ErrorResponse of(int status, String message){
        return new ErrorResponse(status, message);
    }

    public static ErrorResponse of(int status, String message, BindingResult bindingResult){
        return new ErrorResponse(status, message, FieldErrorResponse.of(bindingResult), null);
    }

    public static ErrorResponse of(int status, String message, List<? extends MessageSourceResolvable> messageSourceResolvables){
        return new ErrorResponse(status, message, FieldErrorResponse.of(messageSourceResolvables), null);
    }

    public static ErrorResponse of(int status, String message, TypeMismatchException typeMismatchException){
        return new ErrorResponse(status, message, null, TypeMissResponse.of(typeMismatchException));
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

        public static List<FieldErrorResponse> of(List<? extends MessageSourceResolvable> allErrors){

            return allErrors.stream()
                    .map(error-> {
                        FieldError fieldError = (FieldError)error;
                        return FieldErrorResponse.builder()
                                .field(fieldError.getField())
                                .rejectedValue(fieldError.getRejectedValue())
                                .reason(fieldError.getDefaultMessage())
                                .build();
                    }).collect(Collectors.toList());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class TypeMissResponse {

        Class requiredType;
        String propertyName;
        Object typeMissValue;


        public static TypeMissResponse of(TypeMismatchException exception) {
            return new TypeMissResponse(exception.getRequiredType(), exception.getPropertyName(), exception.getValue());
        }

    }



}
