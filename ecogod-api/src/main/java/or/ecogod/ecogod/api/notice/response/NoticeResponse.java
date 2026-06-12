package or.ecogod.ecogod.api.notice.response;

import or.ecogod.ecogod.domain.notice.domain.model.Notice;

import java.time.LocalDateTime;

public record NoticeResponse(
        Long id,
        String title,
        String summary,
        String content,
        boolean published,
        LocalDateTime publishedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static NoticeResponse from(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getSummary(),
                notice.getContent(),
                notice.isPublished(),
                notice.getPublishedAt(),
                notice.getCreatedAt(),
                notice.getUpdatedAt()
        );
    }
}
