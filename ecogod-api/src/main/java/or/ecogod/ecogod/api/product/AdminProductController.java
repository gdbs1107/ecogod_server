package or.ecogod.ecogod.api.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.api.product.request.AdminProductCreateRequest;
import or.ecogod.ecogod.api.product.request.AdminProductUpdateRequest;
import or.ecogod.ecogod.api.product.response.ProductResponse;
import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.domain.product.application.service.AdminProductService;
import or.ecogod.ecogod.domain.product.domain.model.Product;
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
@RequestMapping("/api/v1/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts() {
        return ResponseEntity.ok(ApiResponse.success(
                adminProductService.getAdminProducts().stream().map(ProductResponse::from).toList()
        ));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.success(ProductResponse.from(adminProductService.getAdminProduct(productId))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody AdminProductCreateRequest request) {
        Product saved = adminProductService.createProduct(
                request.categoryCode(),
                request.name(),
                request.summary(),
                request.description(),
                request.thumbnailUrl(),
                request.published()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(ProductResponse.from(saved)));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody AdminProductUpdateRequest request
    ) {
        Product updated = adminProductService.updateProduct(
                productId,
                request.categoryCode(),
                request.name(),
                request.summary(),
                request.description(),
                request.thumbnailUrl(),
                request.published()
        );
        return ResponseEntity.ok(ApiResponse.success(ProductResponse.from(updated)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        adminProductService.deleteProduct(productId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
