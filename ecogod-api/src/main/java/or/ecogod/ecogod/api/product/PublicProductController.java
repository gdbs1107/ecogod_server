package or.ecogod.ecogod.api.product;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.api.product.response.ProductResponse;
import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.domain.product.application.service.PublicProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class PublicProductController {

    private final PublicProductService publicProductService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts(@RequestParam("category") String categorySlug) {
        return ResponseEntity.ok(ApiResponse.success(
                publicProductService.getProductsByCategorySlug(categorySlug).stream().map(ProductResponse::from).toList()
        ));
    }
}
