package or.ecogad.ecogad.domain.auth.repository;

import or.ecogad.ecogad.domain.auth.domain.model.AdminUser;

import java.util.Optional;

public interface AdminUserRepository {
    AdminUser save(AdminUser adminUser);

    boolean existsByLoginId(String loginId);

    Optional<AdminUser> findByLoginId(String loginId);

    Optional<AdminUser> findById(Long id);
}
