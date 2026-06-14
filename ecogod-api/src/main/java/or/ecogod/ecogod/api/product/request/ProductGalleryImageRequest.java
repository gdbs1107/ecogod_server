package or.ecogod.ecogod.api.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import or.ecogod.ecogod.domain.product.domain.model.ProductGalleryImage;

public record ProductGalleryImageRequest(
        @NotBlank String key,
        @NotBlank String url,
        String altText,
        @NotNull Integer sortOrder,
        @NotNull Boolean primary
) {
    public ProductGalleryImage toDomain() {
        return new ProductGalleryImage(key, url, altText, sortOrder, primary);
    }
}
