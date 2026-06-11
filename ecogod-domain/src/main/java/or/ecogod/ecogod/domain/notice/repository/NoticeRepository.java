package or.ecogod.ecogod.domain.notice.repository;

import or.ecogod.ecogod.domain.notice.domain.model.Notice;

public interface NoticeRepository {
    Notice save(Notice notice);

    boolean existsByTitle(String title);
}
