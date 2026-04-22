package or.ecogad.ecogad.infra.persistence.auth.repository.jpa;

import or.ecogad.ecogad.infra.persistence.auth.entity.AdminUserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserJpaRepository extends JpaRepository<AdminUserJpaEntity, Long> {
    boolean existsByLoginId(String loginId);

    Optional<AdminUserJpaEntity> findByLoginId(String loginId);
}
