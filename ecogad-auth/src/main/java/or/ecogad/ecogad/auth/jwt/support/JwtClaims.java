package or.ecogad.ecogad.auth.jwt.support;

public record JwtClaims(
        Long adminId,
        String role,
        String category
) {
}
