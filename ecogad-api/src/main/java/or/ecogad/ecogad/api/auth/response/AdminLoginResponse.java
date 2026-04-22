package or.ecogad.ecogad.api.auth.response;

import or.ecogad.ecogad.domain.auth.application.result.AdminLoginResult;

public record AdminLoginResponse(
        String accessToken,
        AdminProfileResponse admin
) {
    public static AdminLoginResponse from(AdminLoginResult result) {
        return new AdminLoginResponse(result.accessToken(), AdminProfileResponse.from(result.admin()));
    }
}
