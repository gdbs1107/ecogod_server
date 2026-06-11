package or.ecogod.ecogod.api.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.api.product.request.AdminProductCreateRequest;
import or.ecogod.ecogod.api.product.response.AdminProductCreateResponse;
import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.domain.product.application.result.ProductCreateResult;
import or.ecogod.ecogod.domain.product.application.service.AdminProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping
    public ResponseEntity<ApiResponse<AdminProductCreateResponse>> createProduct(
            @Valid @RequestBody AdminProductCreateRequest request
    ) {
        ProductCreateResult result = adminProductService.createProduct(request.toCommand());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(AdminProductCreateResponse.from(result)));
    }
}
