package or.ecogod.ecogod.api.productcategory.response;

import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;

import java.time.LocalDateTime;

public record ProductCategoryResponse(
        Long id,
        String code,
        String slug,
        String name,
        String description,
        int sortOrder,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProductCategoryResponse from(ProductCategory category) {
        return new ProductCategoryResponse(
                category.getId(),
                category.getCode(),
                category.getSlug(),
                category.getName(),
                category.getDescription(),
                category.getSortOrder(),
                category.isActive(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
