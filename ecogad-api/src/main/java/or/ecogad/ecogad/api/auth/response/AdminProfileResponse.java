package or.ecogad.ecogad.api.auth.response;

import or.ecogad.ecogad.domain.auth.application.result.AdminProfileResult;

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
