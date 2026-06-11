package or.ecogod.ecogod.domain.notice.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Notice {

    private final Long id;
    private final String title;
    private final String content;
    private final boolean published;
    private final LocalDateTime publishedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Notice(
            Long id,
            String title,
            String content,
            boolean published,
            LocalDateTime publishedAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.published = published;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Notice create(String title, String content, boolean published) {
        return new Notice(
                null,
                title,
                content,
                published,
                published ? LocalDateTime.now() : null,
                null,
                null
        );
    }

    public static Notice restore(
            Long id,
            String title,
            String content,
            boolean published,
            LocalDateTime publishedAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new Notice(id, title, content, published, publishedAt, createdAt, updatedAt);
    }
}
