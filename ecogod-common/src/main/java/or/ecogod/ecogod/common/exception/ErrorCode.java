package or.ecogod.ecogod.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C400", "요청 값이 올바르지 않습니다."),
    ACCESS_TOKEN_REQUIRED(HttpStatus.UNAUTHORIZED, "A401", "로그인이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A401", "유효하지 않은 인증 토큰입니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A401", "인증 토큰이 만료되었습니다."),
    REQUEST_ACCESS_DENIED(HttpStatus.FORBIDDEN, "A403", "접근 권한이 없습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "C404", "요청한 리소스를 찾을 수 없습니다."),
    DUPLICATE_ADMIN_LOGIN_ID(HttpStatus.CONFLICT, "A409", "같은 관리자 로그인 ID가 이미 존재합니다."),
    ADMIN_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "A401", "관리자 로그인 정보가 올바르지 않습니다."),
    ADMIN_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "A404", "관리자 계정을 찾을 수 없습니다."),
    INVALID_PRODUCT_CATEGORY(HttpStatus.BAD_REQUEST, "P400", "유효하지 않은 제품 카테고리입니다."),
    PRODUCT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "P404", "제품 카테고리를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "P404", "제품을 찾을 수 없습니다."),
    DUPLICATE_PRODUCT(HttpStatus.CONFLICT, "P409", "동일 카테고리에 같은 이름의 제품이 이미 존재합니다."),
    DUPLICATE_PRODUCT_CATEGORY_CODE(HttpStatus.CONFLICT, "PC409", "같은 카테고리 코드가 이미 존재합니다."),
    DUPLICATE_PRODUCT_CATEGORY_SLUG(HttpStatus.CONFLICT, "PC409", "같은 카테고리 주소가 이미 존재합니다."),
    PRODUCT_CATEGORY_IN_USE(HttpStatus.CONFLICT, "PC409", "이 카테고리를 사용하는 제품이 있어 삭제할 수 없습니다."),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "N404", "공지사항을 찾을 수 없습니다."),
    DUPLICATE_NOTICE(HttpStatus.CONFLICT, "N409", "같은 제목의 공지사항이 이미 존재합니다."),
    INVALID_FILE_UPLOAD(HttpStatus.BAD_REQUEST, "F400", "업로드 파일 형식이 올바르지 않습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F500", "파일 업로드에 실패했습니다."),
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
