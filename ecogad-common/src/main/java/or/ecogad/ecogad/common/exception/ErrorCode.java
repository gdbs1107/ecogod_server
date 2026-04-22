package or.ecogad.ecogad.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C400", "요청 값이 올바르지 않습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "C404", "요청한 리소스를 찾을 수 없습니다."),
    INVALID_PRODUCT_CATEGORY(HttpStatus.BAD_REQUEST, "P400", "유효하지 않은 제품 카테고리입니다."),
    DUPLICATE_PRODUCT(HttpStatus.CONFLICT, "P409", "동일 카테고리에 같은 이름의 제품이 이미 존재합니다."),
    DUPLICATE_NOTICE(HttpStatus.CONFLICT, "N409", "같은 제목의 공지사항이 이미 존재합니다."),
    INQUIRY_NOTIFICATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "I500", "견적 문의 알림 메일 전송에 실패했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C500", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus status() {
        return status;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
