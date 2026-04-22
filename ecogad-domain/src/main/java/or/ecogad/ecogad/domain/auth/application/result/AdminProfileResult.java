package or.ecogad.ecogad.domain.auth.application.result;

public record AdminProfileResult(
        Long id,
        String loginId,
        String name,
        String role
) {
}
