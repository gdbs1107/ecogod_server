package or.ecogod.ecogod.api.productcategory;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.api.productcategory.response.ProductCategoryResponse;
import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.domain.product.application.service.PublicProductCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-categories")
public class ProductCategoryController {

    private final PublicProductCategoryService publicProductCategoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductCategoryResponse>>> getCategories() {
        return ResponseEntity.ok(ApiResponse.success(
                publicProductCategoryService.getCategories().stream().map(ProductCategoryResponse::from).toList()
        ));
    }
}
