package or.ecogod.ecogod.domain.product.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
public class Product {

    private final Long id;
    private final ProductCategory category;
    private final String name;
    private final String summary;
    private final String description;
    private final String thumbnailUrl;
    private final List<ProductDetailImage> detailImages;
    private final boolean published;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Product(
            Long id,
            ProductCategory category,
            String name,
            String summary,
            String description,
            String thumbnailUrl,
            List<ProductDetailImage> detailImages,
            boolean published,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.category = category;
        this.name = normalizeText(name);
        this.summary = normalizeNullable(summary);
        this.description = normalizeNullable(description);
        this.thumbnailUrl = normalizeNullable(thumbnailUrl);
        this.detailImages = normalizeDetailImages(detailImages);
        this.published = published;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Product create(
            ProductCategory category,
            String name,
            String summary,
            String description,
            String thumbnailUrl,
            List<ProductDetailImage> detailImages,
            boolean published
    ) {
        return new Product(null, category, name, summary, description, thumbnailUrl, detailImages, published, null, null);
    }

    public static Product restore(
            Long id,
            ProductCategory category,
            String name,
            String summary,
            String description,
            String thumbnailUrl,
            List<ProductDetailImage> detailImages,
            boolean published,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new Product(id, category, name, summary, description, thumbnailUrl, detailImages, published, createdAt, updatedAt);
    }

    public Product update(
            ProductCategory category,
            String name,
            String summary,
            String description,
            String thumbnailUrl,
            List<ProductDetailImage> detailImages,
            boolean published
    ) {
        return new Product(id, category, name, summary, description, thumbnailUrl, detailImages, published, createdAt, updatedAt);
    }

    private static String normalizeText(String value) {
        return value == null ? "" : value.trim();
    }

    private static String normalizeNullable(String value) {
        String normalized = normalizeText(value);
        return normalized.isBlank() ? null : normalized;
    }

    private static List<ProductDetailImage> normalizeDetailImages(List<ProductDetailImage> detailImages) {
        if (detailImages == null) {
            return List.of();
        }
        return detailImages.stream()
                .sorted(Comparator.comparingInt(ProductDetailImage::sortOrder))
                .toList();
    }
}
