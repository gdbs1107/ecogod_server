package or.ecogod.ecogod.domain.product.repository;

import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository {
    ProductCategory save(ProductCategory category);

    Optional<ProductCategory> findById(Long id);

    Optional<ProductCategory> findByCode(String code);

    Optional<ProductCategory> findBySlug(String slug);

    List<ProductCategory> findAll();

    List<ProductCategory> findAllActive();

    boolean existsByCode(String code);

    boolean existsBySlug(String slug);

    void deleteById(Long id);
}
