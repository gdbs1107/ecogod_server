package or.ecogod.ecogod.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import or.ecogod.ecogod.common.exception.ErrorCode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class ApiErrorResponseWriter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules();

    private ApiErrorResponseWriter() {
    }

    public static void write(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.status().value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");
        response.getWriter().write(
                OBJECT_MAPPER.writeValueAsString(ApiResponse.failure(errorCode.code(), errorCode.message()))
        );
    }
}
