package or.ecogod.ecogod.domain.product.domain.model;

public record ProductGalleryImage(
        String key,
        String url,
        String altText,
        int sortOrder,
        boolean primary
) {
}
