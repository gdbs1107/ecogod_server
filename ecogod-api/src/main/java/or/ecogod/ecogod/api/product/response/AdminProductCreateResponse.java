package or.ecogod.ecogod.api.product.response;

import or.ecogod.ecogod.domain.product.application.result.ProductCreateResult;

import java.time.LocalDateTime;

public record AdminProductCreateResponse(
        Long id,
        String category,
        String name,
        String summary,
        String description,
        String thumbnailUrl,
        boolean published,
        LocalDateTime createdAt
) {
    public static AdminProductCreateResponse from(ProductCreateResult result) {
        return new AdminProductCreateResponse(
                result.id(),
                result.category(),
                result.name(),
                result.summary(),
                result.description(),
                result.thumbnailUrl(),
                result.published(),
                result.createdAt()
        );
    }
}
