package or.ecogod.ecogod.api.product.response;

import or.ecogod.ecogod.domain.product.domain.model.ProductGalleryImage;

public record ProductGalleryImageResponse(String key, String url, String altText, int sortOrder, boolean primary) {
    public static ProductGalleryImageResponse from(ProductGalleryImage image) {
        return new ProductGalleryImageResponse(image.key(), image.url(), image.altText(), image.sortOrder(), image.primary());
    }
}
