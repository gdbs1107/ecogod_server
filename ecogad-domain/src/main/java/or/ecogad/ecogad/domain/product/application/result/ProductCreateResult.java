package or.ecogad.ecogad.domain.product.application.result;

import java.time.LocalDateTime;

public record ProductCreateResult(
        Long id,
        String category,
        String name,
        String summary,
        String description,
        String thumbnailUrl,
        boolean published,
        LocalDateTime createdAt
) {
}
