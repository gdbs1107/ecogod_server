package or.ecogod.ecogod.auth.jwt.support;

public record JwtClaims(
        Long adminId,
        String role,
        String category
) {
}
