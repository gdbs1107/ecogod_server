package or.ecogod.ecogod.infra.persistence.auth.repository;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.domain.auth.domain.model.AdminUser;
import or.ecogod.ecogod.domain.auth.repository.AdminUserRepository;
import or.ecogod.ecogod.infra.persistence.auth.entity.AdminUserJpaEntity;
import or.ecogod.ecogod.infra.persistence.auth.repository.jpa.AdminUserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdminUserRepositoryImpl implements AdminUserRepository {

    private final AdminUserJpaRepository adminUserJpaRepository;

    @Override
    public AdminUser save(AdminUser adminUser) {
        return toDomain(adminUserJpaRepository.save(toJpa(adminUser)));
    }

    @Override
    public boolean existsByLoginId(String loginId) {
        return adminUserJpaRepository.existsByLoginId(loginId);
    }

    @Override
    public Optional<AdminUser> findByLoginId(String loginId) {
        return adminUserJpaRepository.findByLoginId(loginId).map(this::toDomain);
    }

    @Override
    public Optional<AdminUser> findById(Long id) {
        return adminUserJpaRepository.findById(id).map(this::toDomain);
    }

    private AdminUserJpaEntity toJpa(AdminUser adminUser) {
        return AdminUserJpaEntity.builder()
                .id(adminUser.getId())
                .loginId(adminUser.getLoginId())
                .passwordHash(adminUser.getPasswordHash())
                .name(adminUser.getName())
                .role(adminUser.getRole())
                .build();
    }

    private AdminUser toDomain(AdminUserJpaEntity entity) {
        return AdminUser.restore(
                entity.getId(),
                entity.getLoginId(),
                entity.getPasswordHash(),
                entity.getName(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
