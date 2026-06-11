package or.ecogod.ecogod.domain.notice.application.result;

import java.time.LocalDateTime;

public record NoticeCreateResult(
        Long id,
        String title,
        String content,
        boolean published,
        LocalDateTime publishedAt,
        LocalDateTime createdAt
) {
}
