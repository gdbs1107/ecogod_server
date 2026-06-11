package or.ecogod.ecogod.api.auth.response;

import or.ecogod.ecogod.domain.auth.application.result.AdminProfileResult;

public record AdminProfileResponse(
        Long id,
        String loginId,
        String name,
        String role
) {
    public static AdminProfileResponse from(AdminProfileResult result) {
        return new AdminProfileResponse(result.id(), result.loginId(), result.name(), result.role());
    }
}
