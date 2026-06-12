package or.ecogod.ecogod.domain.product.application.service;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;
import or.ecogod.ecogod.domain.product.repository.ProductCategoryRepository;
import or.ecogod.ecogod.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;

    public List<ProductCategory> getCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory getCategory(Long categoryId) {
        return productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
    }

    @Transactional
    public ProductCategory createCategory(String code, String slug, String name, String description, int sortOrder, boolean active) {
        ProductCategory category = ProductCategory.create(code, slug, name, description, sortOrder, active);

        if (productCategoryRepository.existsByCode(category.getCode())) {
            throw new CustomException(ErrorCode.DUPLICATE_PRODUCT_CATEGORY_CODE);
        }
        if (productCategoryRepository.existsBySlug(category.getSlug())) {
            throw new CustomException(ErrorCode.DUPLICATE_PRODUCT_CATEGORY_SLUG);
        }

        return productCategoryRepository.save(category);
    }

    @Transactional
    public ProductCategory updateCategory(Long categoryId, String name, String description, int sortOrder, boolean active) {
        ProductCategory current = getCategory(categoryId);
        return productCategoryRepository.save(current.update(name, description, sortOrder, active));
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        ProductCategory category = getCategory(categoryId);
        if (productRepository.existsByCategoryId(category.getId())) {
            throw new CustomException(ErrorCode.PRODUCT_CATEGORY_IN_USE);
        }
        productCategoryRepository.deleteById(categoryId);
    }
}
