package or.ecogod.ecogod.api.productcategory.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminCategoryCreateRequest(
        @NotBlank(message = "카테고리 코드를 입력해 주세요.")
        String code,
        @NotBlank(message = "카테고리 주소(slug)를 입력해 주세요.")
        String slug,
        @NotBlank(message = "카테고리명을 입력해 주세요.")
        String name,
        String description,
        @NotNull(message = "정렬 순서를 입력해 주세요.")
        Integer sortOrder,
        @NotNull(message = "사용 여부를 선택해 주세요.")
        Boolean isActive
) {
}
