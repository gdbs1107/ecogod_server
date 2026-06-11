package or.ecogod.ecogod.api.exception;

import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity
                .status(errorCode.status())
                .body(ApiResponse.failure(errorCode.code(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String message = fieldError == null ? ErrorCode.INVALID_INPUT_VALUE.message() : fieldError.getDefaultMessage();

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT_VALUE.status())
                .body(ApiResponse.failure(ErrorCode.INVALID_INPUT_VALUE.code(), message));
    }

    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<ApiResponse<Void>> handleNotFound(Exception exception) {
        return ResponseEntity
                .status(ErrorCode.RESOURCE_NOT_FOUND.status())
                .body(ApiResponse.failure(ErrorCode.RESOURCE_NOT_FOUND.code(), ErrorCode.RESOURCE_NOT_FOUND.message()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.status())
                .body(ApiResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.code(), ErrorCode.INTERNAL_SERVER_ERROR.message()));
    }
}
