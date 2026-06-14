package or.ecogod.ecogod.domain.product.domain.model;

public record ProductDetailImage(
        String key,
        String url,
        String altText,
        int sortOrder
) {
    public ProductDetailImage {
        key = normalize(key);
        url = normalize(url);
        altText = normalize(altText);
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
