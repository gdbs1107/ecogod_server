package or.ecogod.ecogod.infra.persistence.notice.repository;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.domain.notice.domain.model.Notice;
import or.ecogod.ecogod.domain.notice.repository.NoticeRepository;
import or.ecogod.ecogod.infra.persistence.notice.entity.NoticeJpaEntity;
import or.ecogod.ecogod.infra.persistence.notice.repository.jpa.NoticeJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        return noticeJpaRepository.existsByTitle(title.trim());
    }

    @Override
    public boolean existsByTitleAndIdNot(String title, Long noticeId) {
        return noticeJpaRepository.existsByTitleAndIdNot(title.trim(), noticeId);
    }

    @Override
    public Optional<Notice> findById(Long id) {
        return noticeJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Notice> findPublishedById(Long id) {
        return noticeJpaRepository.findByIdAndPublishedTrue(id).map(this::toDomain);
    }

    @Override
    public List<Notice> findAll() {
        return noticeJpaRepository.findAllByOrderByPublishedAtDescUpdatedAtDesc().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Notice> findAllPublished() {
        return noticeJpaRepository.findByPublishedTrueOrderByPublishedAtDescUpdatedAtDesc().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        noticeJpaRepository.deleteById(id);
    }

    private NoticeJpaEntity toJpa(Notice notice) {
        return NoticeJpaEntity.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .summary(notice.getSummary())
                .content(notice.getContent())
                .published(notice.isPublished())
                .publishedAt(notice.getPublishedAt())
                .build();
    }

    private Notice toDomain(NoticeJpaEntity entity) {
        return Notice.restore(
                entity.getId(),
                entity.getTitle(),
                entity.getSummary(),
                entity.getContent(),
                entity.isPublished(),
                entity.getPublishedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
