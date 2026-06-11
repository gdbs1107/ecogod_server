package or.ecogod.ecogod.infra.persistence.notice.repository.jpa;

import or.ecogod.ecogod.infra.persistence.notice.entity.NoticeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeJpaRepository extends JpaRepository<NoticeJpaEntity, Long> {
    boolean existsByTitle(String title);
}
