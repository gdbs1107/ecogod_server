package or.ecogod.ecogod.auth.jwt.support;

import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;

public final class BearerTokenExtractor {

    private static final String PREFIX = "Bearer ";

    private BearerTokenExtractor() {
    }

    public static String extract(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(PREFIX)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        return authorizationHeader.substring(PREFIX.length()).trim();
    }
}
