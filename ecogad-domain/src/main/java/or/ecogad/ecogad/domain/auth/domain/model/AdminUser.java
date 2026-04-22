package or.ecogad.ecogad.domain.auth.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminUser {

    private final Long id;
    private final String loginId;
    private final String passwordHash;
    private final String name;
    private final AdminRole role;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private AdminUser(
            Long id,
            String loginId,
            String passwordHash,
            String name,
            AdminRole role,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.loginId = loginId;
        this.passwordHash = passwordHash;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AdminUser create(String loginId, String passwordHash, String name) {
        return new AdminUser(null, loginId, passwordHash, name, AdminRole.ADMIN, null, null);
    }

    public static AdminUser restore(
            Long id,
            String loginId,
            String passwordHash,
            String name,
            AdminRole role,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new AdminUser(id, loginId, passwordHash, name, role, createdAt, updatedAt);
    }
}
