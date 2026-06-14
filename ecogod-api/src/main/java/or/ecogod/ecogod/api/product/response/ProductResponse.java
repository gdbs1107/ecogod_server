package or.ecogod.ecogod.api.product.response;

import or.ecogod.ecogod.domain.product.domain.model.Product;

import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        Long id,
        String categoryCode,
        String categorySlug,
        String categoryName,
        String name,
        String summary,
        String description,
        String thumbnailUrl,
        List<ProductGalleryImageResponse> galleryImages,
        List<ProductDetailImageResponse> detailImages,
        boolean published,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getCategory().getCode(),
                product.getCategory().getSlug(),
                product.getCategory().getName(),
                product.getName(),
                product.getSummary(),
                product.getDescription(),
                product.getThumbnailUrl(),
                product.getGalleryImages().stream().map(ProductGalleryImageResponse::from).toList(),
                product.getDetailImages().stream().map(ProductDetailImageResponse::from).toList(),
                product.isPublished(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
