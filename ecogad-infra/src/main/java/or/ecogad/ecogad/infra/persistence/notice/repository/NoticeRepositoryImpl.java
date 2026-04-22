package or.ecogad.ecogad.infra.persistence.notice.repository;

import lombok.RequiredArgsConstructor;
import or.ecogad.ecogad.domain.notice.domain.model.Notice;
import or.ecogad.ecogad.domain.notice.repository.NoticeRepository;
import or.ecogad.ecogad.infra.persistence.notice.entity.NoticeJpaEntity;
import or.ecogad.ecogad.infra.persistence.notice.repository.jpa.NoticeJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {

    private final NoticeJpaRepository noticeJpaRepository;

    @Override
    public Notice save(Notice notice) {
        NoticeJpaEntity saved = noticeJpaRepository.save(toJpa(notice));
        return toDomain(saved);
    }

    @Override
    public boolean existsByTitle(String title) {
        return noticeJpaRepository.existsByTitle(title);
    }

    private NoticeJpaEntity toJpa(Notice notice) {
        return NoticeJpaEntity.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .published(notice.isPublished())
                .publishedAt(notice.getPublishedAt())
                .build();
    }

    private Notice toDomain(NoticeJpaEntity entity) {
        return Notice.restore(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.isPublished(),
                entity.getPublishedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
