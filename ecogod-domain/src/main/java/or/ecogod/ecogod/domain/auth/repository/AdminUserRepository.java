package or.ecogod.ecogod.domain.auth.repository;

import or.ecogod.ecogod.domain.auth.domain.model.AdminUser;

import java.util.Optional;

public interface AdminUserRepository {
    AdminUser save(AdminUser adminUser);

    boolean existsByLoginId(String loginId);

    Optional<AdminUser> findByLoginId(String loginId);

    Optional<AdminUser> findById(Long id);
}
