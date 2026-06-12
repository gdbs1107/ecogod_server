package or.ecogod.ecogod.api.productcategory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.api.productcategory.request.AdminCategoryCreateRequest;
import or.ecogod.ecogod.api.productcategory.request.AdminCategoryUpdateRequest;
import or.ecogod.ecogod.api.productcategory.response.ProductCategoryResponse;
import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.domain.product.application.service.AdminProductCategoryService;
import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/categories")
public class AdminCategoryController {

    private final AdminProductCategoryService adminProductCategoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductCategoryResponse>>> getCategories() {
        return ResponseEntity.ok(ApiResponse.success(
                adminProductCategoryService.getCategories().stream().map(ProductCategoryResponse::from).toList()
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductCategoryResponse>> createCategory(@Valid @RequestBody AdminCategoryCreateRequest request) {
        ProductCategory saved = adminProductCategoryService.createCategory(
                request.code(),
                request.slug(),
                request.name(),
                request.description(),
                request.sortOrder(),
                request.isActive()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(ProductCategoryResponse.from(saved)));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<ProductCategoryResponse>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody AdminCategoryUpdateRequest request
    ) {
        ProductCategory updated = adminProductCategoryService.updateCategory(
                categoryId,
                request.name(),
                request.description(),
                request.sortOrder(),
                request.isActive()
        );
        return ResponseEntity.ok(ApiResponse.success(ProductCategoryResponse.from(updated)));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {
        adminProductCategoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
