package or.ecogad.ecogad.domain.product.application.command;

import or.ecogad.ecogad.domain.product.domain.model.ProductCategory;

public record ProductCreateCommand(
        ProductCategory category,
        String name,
        String summary,
        String description,
        String thumbnailUrl,
        boolean published
) {
}
