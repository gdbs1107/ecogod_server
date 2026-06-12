package or.ecogod.ecogod.domain.notice.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Notice {

    private final Long id;
    private final String title;
    private final String summary;
    private final String content;
    private final boolean published;
    private final LocalDateTime publishedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Notice(
            Long id,
            String title,
            String summary,
            String content,
            boolean published,
            LocalDateTime publishedAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = normalizeText(title);
        this.summary = normalizeText(summary);
        this.content = normalizeText(content);
        this.published = published;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Notice create(String title, String summary, String content, boolean published) {
        return new Notice(null, title, summary, content, published, published ? LocalDateTime.now() : null, null, null);
    }

    public static Notice restore(
            Long id,
            String title,
            String summary,
            String content,
            boolean published,
            LocalDateTime publishedAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new Notice(id, title, summary, content, published, publishedAt, createdAt, updatedAt);
    }

    public Notice update(String title, String summary, String content, boolean published) {
        LocalDateTime nextPublishedAt = published ? (publishedAt == null ? LocalDateTime.now() : publishedAt) : null;
        return new Notice(id, title, summary, content, published, nextPublishedAt, createdAt, updatedAt);
    }

    private static String normalizeText(String value) {
        return value == null ? "" : value.trim();
    }
}
