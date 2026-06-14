package or.ecogod.ecogod.api.product.response;

import or.ecogod.ecogod.domain.product.domain.model.ProductDetailImage;

public record ProductDetailImageResponse(String key, String url, String altText, int sortOrder) {
    public static ProductDetailImageResponse from(ProductDetailImage image) {
        return new ProductDetailImageResponse(image.key(), image.url(), image.altText(), image.sortOrder());
    }
}
