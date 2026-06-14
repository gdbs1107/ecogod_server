package or.ecogod.ecogod.api.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import or.ecogod.ecogod.domain.product.domain.model.ProductDetailImage;

public record ProductDetailImageRequest(
        @NotBlank String key,
        @NotBlank String url,
        String altText,
        @NotNull Integer sortOrder
) {
    public ProductDetailImage toDomain() {
        return new ProductDetailImage(key, url, altText, sortOrder);
    }
}
