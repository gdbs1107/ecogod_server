package or.ecogod.ecogod.domain.product.application.command;

import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;

public record ProductCreateCommand(
        ProductCategory category,
        String name,
        String summary,
        String description,
        String thumbnailUrl,
        boolean published
) {
}
