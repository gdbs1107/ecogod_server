package or.ecogod.ecogod.infra.persistence.product.repository;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.domain.product.domain.model.Product;
import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;
import or.ecogod.ecogod.domain.product.repository.ProductRepository;
import or.ecogod.ecogod.infra.persistence.product.entity.ProductJpaEntity;
import or.ecogod.ecogod.infra.persistence.product.repository.jpa.ProductJpaRepository;
import or.ecogod.ecogod.infra.persistence.productcategory.entity.ProductCategoryJpaEntity;
import or.ecogod.ecogod.infra.persistence.productcategory.repository.jpa.ProductCategoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductCategoryJpaRepository productCategoryJpaRepository;

    @Override
    public Product save(Product product) {
        ProductJpaEntity saved = productJpaRepository.save(toJpa(product));
        return toDomain(saved);
    }

    @Override
    public boolean existsByCategoryIdAndName(Long categoryId, String name) {
        return productJpaRepository.existsByCategoryIdAndNameIgnoreCase(categoryId, name.trim());
    }

    @Override
    public boolean existsByCategoryIdAndNameAndIdNot(Long categoryId, String name, Long productId) {
        return productJpaRepository.existsByCategoryIdAndNameIgnoreCaseAndIdNot(categoryId, name.trim(), productId);
    }

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return productJpaRepository.existsByCategoryId(categoryId);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAllByOrderByUpdatedAtDesc().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Product> findPublishedByCategorySlug(String categorySlug) {
        return productJpaRepository.findByPublishedTrueAndCategory_SlugOrderByUpdatedAtDesc(categorySlug).stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        productJpaRepository.deleteById(id);
    }

    private ProductJpaEntity toJpa(Product product) {
        ProductCategoryJpaEntity categoryJpaEntity = productCategoryJpaRepository.getReferenceById(product.getCategory().getId());

        return ProductJpaEntity.builder()
                .id(product.getId())
                .category(categoryJpaEntity)
                .name(product.getName())
                .summary(product.getSummary())
                .description(product.getDescription())
                .thumbnailUrl(product.getThumbnailUrl())
                .published(product.isPublished())
                .build();
    }

    private Product toDomain(ProductJpaEntity entity) {
        ProductCategory category = ProductCategory.restore(
                entity.getCategory().getId(),
                entity.getCategory().getCode(),
                entity.getCategory().getSlug(),
                entity.getCategory().getName(),
                entity.getCategory().getDescription(),
                entity.getCategory().getSortOrder(),
                entity.getCategory().isActive(),
                entity.getCategory().getCreatedAt(),
                entity.getCategory().getUpdatedAt()
        );

        return Product.restore(
                entity.getId(),
                category,
                entity.getName(),
                entity.getSummary(),
                entity.getDescription(),
                entity.getThumbnailUrl(),
                entity.isPublished(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
