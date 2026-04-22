package or.ecogad.ecogad.api.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import or.ecogad.ecogad.domain.product.application.command.ProductCreateCommand;
import or.ecogad.ecogad.domain.product.domain.model.ProductCategory;

public record AdminProductCreateRequest(
        @NotBlank(message = "카테고리는 필수입니다.")
        String category,

        @NotBlank(message = "제품명은 필수입니다.")
        @Size(max = 150, message = "제품명은 150자 이하여야 합니다.")
        String name,

        @Size(max = 255, message = "요약은 255자 이하여야 합니다.")
        String summary,

        String description,

        @Size(max = 500, message = "썸네일 URL은 500자 이하여야 합니다.")
        String thumbnailUrl,

        boolean published
) {
    public ProductCreateCommand toCommand() {
        return new ProductCreateCommand(
                ProductCategory.from(category),
                name,
                summary,
                description,
                thumbnailUrl,
                published
        );
    }
}
