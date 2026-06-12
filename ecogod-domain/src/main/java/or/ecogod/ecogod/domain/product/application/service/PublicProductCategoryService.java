package or.ecogod.ecogod.domain.product.application.service;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;
import or.ecogod.ecogod.domain.product.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getCategories() {
        return productCategoryRepository.findAllActive();
    }

    public ProductCategory getCategoryBySlug(String slug) {
        return productCategoryRepository.findBySlug(slug)
                .filter(ProductCategory::isActive)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
    }
}
