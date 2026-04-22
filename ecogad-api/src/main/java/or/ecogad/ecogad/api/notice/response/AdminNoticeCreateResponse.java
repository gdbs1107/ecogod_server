package or.ecogad.ecogad.api.notice.response;

import or.ecogad.ecogad.domain.notice.application.result.NoticeCreateResult;

import java.time.LocalDateTime;

public record AdminNoticeCreateResponse(
        Long id,
        String title,
        String content,
        boolean published,
        LocalDateTime publishedAt,
        LocalDateTime createdAt
) {
    public static AdminNoticeCreateResponse from(NoticeCreateResult result) {
        return new AdminNoticeCreateResponse(
                result.id(),
                result.title(),
                result.content(),
                result.published(),
                result.publishedAt(),
                result.createdAt()
        );
    }
}
