package or.ecogod.ecogod.infra.persistence.notice.repository.jpa;

import or.ecogod.ecogod.infra.persistence.notice.entity.NoticeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeJpaRepository extends JpaRepository<NoticeJpaEntity, Long> {
    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);

    Optional<NoticeJpaEntity> findByIdAndPublishedTrue(Long id);

    List<NoticeJpaEntity> findAllByOrderByPublishedAtDescUpdatedAtDesc();

    List<NoticeJpaEntity> findByPublishedTrueOrderByPublishedAtDescUpdatedAtDesc();
}
