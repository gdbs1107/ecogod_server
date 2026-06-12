package or.ecogod.ecogod.infra.persistence.productcategory.repository;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;
import or.ecogod.ecogod.domain.product.repository.ProductCategoryRepository;
import or.ecogod.ecogod.infra.persistence.productcategory.entity.ProductCategoryJpaEntity;
import or.ecogod.ecogod.infra.persistence.productcategory.repository.jpa.ProductCategoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

    private final ProductCategoryJpaRepository productCategoryJpaRepository;

    @Override
    public ProductCategory save(ProductCategory category) {
        ProductCategoryJpaEntity saved = productCategoryJpaRepository.save(toJpa(category));
        return toDomain(saved);
    }

    @Override
    public Optional<ProductCategory> findById(Long id) {
        return productCategoryJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<ProductCategory> findByCode(String code) {
        return productCategoryJpaRepository.findByCode(code == null ? "" : code.trim().toUpperCase()).map(this::toDomain);
    }

    @Override
    public Optional<ProductCategory> findBySlug(String slug) {
        return productCategoryJpaRepository.findBySlug(slug == null ? "" : slug.trim().toLowerCase()).map(this::toDomain);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryJpaRepository.findAllByOrderBySortOrderAscNameAsc().stream().map(this::toDomain).toList();
    }

    @Override
    public List<ProductCategory> findAllActive() {
        return productCategoryJpaRepository.findByActiveTrueOrderBySortOrderAscNameAsc().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return productCategoryJpaRepository.existsByCode(code == null ? "" : code.trim().toUpperCase());
    }

    @Override
    public boolean existsBySlug(String slug) {
        return productCategoryJpaRepository.existsBySlug(slug == null ? "" : slug.trim().toLowerCase());
    }

    @Override
    public void deleteById(Long id) {
        productCategoryJpaRepository.deleteById(id);
    }

    private ProductCategoryJpaEntity toJpa(ProductCategory category) {
        return ProductCategoryJpaEntity.builder()
                .id(category.getId())
                .code(category.getCode())
                .slug(category.getSlug())
                .name(category.getName())
                .description(category.getDescription())
                .sortOrder(category.getSortOrder())
                .active(category.isActive())
                .build();
    }

    private ProductCategory toDomain(ProductCategoryJpaEntity entity) {
        return ProductCategory.restore(
                entity.getId(),
                entity.getCode(),
                entity.getSlug(),
                entity.getName(),
                entity.getDescription(),
                entity.getSortOrder(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
