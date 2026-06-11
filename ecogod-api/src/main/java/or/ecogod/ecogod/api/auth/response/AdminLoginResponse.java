package or.ecogod.ecogod.api.auth.response;

import or.ecogod.ecogod.domain.auth.application.result.AdminLoginResult;

public record AdminLoginResponse(
        String accessToken,
        AdminProfileResponse admin
) {
    public static AdminLoginResponse from(AdminLoginResult result) {
        return new AdminLoginResponse(result.accessToken(), AdminProfileResponse.from(result.admin()));
    }
}
