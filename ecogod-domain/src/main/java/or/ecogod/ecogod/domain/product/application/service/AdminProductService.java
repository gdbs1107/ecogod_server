package or.ecogod.ecogod.domain.product.application.service;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import or.ecogod.ecogod.domain.product.domain.model.Product;
import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;
import or.ecogod.ecogod.domain.product.domain.model.ProductDetailImage;
import or.ecogod.ecogod.domain.product.domain.model.ProductGalleryImage;
import or.ecogod.ecogod.domain.product.repository.ProductCategoryRepository;
import or.ecogod.ecogod.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public List<Product> getAdminProducts() {
        return productRepository.findAll();
    }

    public Product getAdminProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Transactional
    public Product createProduct(String categoryCode, String name, String summary, String description, String thumbnailUrl, List<ProductGalleryImage> galleryImages, List<ProductDetailImage> detailImages, boolean published) {
        ProductCategory category = getCategoryByCode(categoryCode);
        if (productRepository.existsByCategoryIdAndName(category.getId(), name)) {
            throw new CustomException(ErrorCode.DUPLICATE_PRODUCT);
        }
        return productRepository.save(Product.create(category, name, summary, description, thumbnailUrl, galleryImages, detailImages, published));
    }

    @Transactional
    public Product createProduct(String categoryCode, String name, String summary, String description, String thumbnailUrl, boolean published) {
        return createProduct(categoryCode, name, summary, description, thumbnailUrl, List.of(), List.of(), published);
    }

    @Transactional
    public Product updateProduct(Long productId, String categoryCode, String name, String summary, String description, String thumbnailUrl, List<ProductGalleryImage> galleryImages, List<ProductDetailImage> detailImages, boolean published) {
        Product current = getAdminProduct(productId);
        ProductCategory category = getCategoryByCode(categoryCode);
        if (productRepository.existsByCategoryIdAndNameAndIdNot(category.getId(), name, productId)) {
            throw new CustomException(ErrorCode.DUPLICATE_PRODUCT);
        }
        return productRepository.save(current.update(category, name, summary, description, thumbnailUrl, galleryImages, detailImages, published));
    }

    @Transactional
    public void deleteProduct(Long productId) {
        getAdminProduct(productId);
        productRepository.deleteById(productId);
    }

    private ProductCategory getCategoryByCode(String categoryCode) {
        return productCategoryRepository.findByCode(categoryCode)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
    }
}
