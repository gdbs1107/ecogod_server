package or.ecogad.ecogad.domain.auth.application.result;

public record AdminLoginResult(
        String accessToken,
        AdminProfileResult admin
) {
}
