package or.ecogod.ecogod.api.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminProductCreateRequest(
        @NotBlank(message = "카테고리를 선택해 주세요.")
        String categoryCode,
        @NotBlank(message = "제품명을 입력해 주세요.")
        String name,
        String summary,
        String description,
        String thumbnailUrl,
        @NotNull(message = "공개 여부를 선택해 주세요.")
        Boolean published
) {
}
