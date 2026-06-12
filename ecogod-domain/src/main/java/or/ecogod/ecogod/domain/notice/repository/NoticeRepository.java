package or.ecogod.ecogod.domain.notice.repository;

import or.ecogod.ecogod.domain.notice.domain.model.Notice;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository {
    Notice save(Notice notice);

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long noticeId);

    Optional<Notice> findById(Long id);

    Optional<Notice> findPublishedById(Long id);

    List<Notice> findAll();

    List<Notice> findAllPublished();

    void deleteById(Long id);
}
