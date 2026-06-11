package or.ecogod.ecogod.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import or.ecogod.ecogod.auth.config.JwtProperties;
import or.ecogod.ecogod.auth.jwt.support.JwtClaims;
import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import or.ecogod.ecogod.domain.auth.domain.model.AdminUser;
import or.ecogod.ecogod.domain.auth.port.TokenProvider;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String createAccessToken(AdminUser adminUser) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(jwtProperties.getAccessTokenValidityInSeconds());

        return Jwts.builder()
                .setSubject(adminUser.getLoginId())
                .claim("adminId", adminUser.getId())
                .claim("role", adminUser.getRole().name())
                .claim("category", "access")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiresAt))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtClaims parseAccessToken(String token) throws ExpiredJwtException, JwtException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String category = claims.get("category", String.class);
        if (!"access".equals(category)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        Number adminId = claims.get("adminId", Number.class);
        if (adminId == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String role = claims.get("role", String.class);
        if (role == null || role.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        return new JwtClaims(adminId.longValue(), role, category);
    }

    public String getHeaderName() {
        return jwtProperties.getHeader();
    }
}
