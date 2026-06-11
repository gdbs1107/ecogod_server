package or.ecogod.ecogod.domain.auth.application.result;

public record AdminLoginResult(
        String accessToken,
        AdminProfileResult admin
) {
}
