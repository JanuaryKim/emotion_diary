package emotion.diary.server.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    EXPIRED_JWT_TOKEN(401, "만료된 토큰"),
    WRONG_SIGNATURE_JWT_TOKEN(401, "잘못된 사인의 토큰"),
    NOT_SUPPORTED_JWT_TOKEN(401, "지원하지않는 형식의 토큰"),
    MALFORMED_JWT_TOKEN(401, "잘못된 형식의 토큰"),
    INVALID_JWT_TOKEN(401, "유효하지 않은 토큰"),

    NOT_EXISTS_DIARY(404, "존재하지 않는 일기"),
    NOT_OWN_DIARY(403, "자신 소유의 일기가 아님"),
    NOT_EXISTS_DIARY_IMAGE_DATA(404, "존재하지 않는 일기 이미지 데이터"),
    NOT_EXISTS_DIARY_IMAGE_FILE(404, "존재하지 않는 일기 이미지 파일"),
    EXCEPTION_FROM_SERVER(500, "서버측의 예상치 못한 에러 발생"),

    PATH_VARIABLE_TYPE_INCORRECT(400, "Path Variable의 타입이 올바르지 않음"),
    UNAUTHENTICATED_REQUEST(401, "인증이 되지 않은 요청입니다"),
    DTO_VALIDATION_FAILED(400, "DTO의 필드 유효성 검증 에러");


    private int code;
    private String sentence;

    ExceptionCode(int code, String sentence) {
        this.code = code;
        this.sentence = sentence;
    }
}
