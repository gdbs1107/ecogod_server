package or.ecogod.ecogod.domain.product.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Locale;

@Getter
public class ProductCategory {

    private final Long id;
    private final String code;
    private final String slug;
    private final String name;
    private final String description;
    private final int sortOrder;
    private final boolean active;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private ProductCategory(
            Long id,
            String code,
            String slug,
            String name,
            String description,
            int sortOrder,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.slug = slug;
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductCategory create(String code, String slug, String name, String description, int sortOrder, boolean active) {
        return new ProductCategory(
                null,
                normalizeCode(code),
                normalizeSlug(slug),
                normalizeText(name),
                normalizeNullable(description),
                sortOrder,
                active,
                null,
                null
        );
    }

    public static ProductCategory restore(
            Long id,
            String code,
            String slug,
            String name,
            String description,
            int sortOrder,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new ProductCategory(
                id,
                normalizeCode(code),
                normalizeSlug(slug),
                normalizeText(name),
                normalizeNullable(description),
                sortOrder,
                active,
                createdAt,
                updatedAt
        );
    }

    public ProductCategory update(String name, String description, int sortOrder, boolean active) {
        return new ProductCategory(id, code, slug, normalizeText(name), normalizeNullable(description), sortOrder, active, createdAt, updatedAt);
    }

    private static String normalizeCode(String value) {
        return value == null ? "" : value.trim().toUpperCase(Locale.ROOT);
    }

    private static String normalizeSlug(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }

    private static String normalizeText(String value) {
        return value == null ? "" : value.trim();
    }

    private static String normalizeNullable(String value) {
        String normalized = normalizeText(value);
        return normalized.isBlank() ? null : normalized;
    }
}
