package or.ecogod.ecogod.infra.persistence.product.repository;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.domain.product.domain.model.Product;
import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;
import or.ecogod.ecogod.domain.product.repository.ProductRepository;
import or.ecogod.ecogod.infra.persistence.product.entity.ProductJpaEntity;
import or.ecogod.ecogod.infra.persistence.product.repository.jpa.ProductJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        ProductJpaEntity saved = productJpaRepository.save(toJpa(product));
        return toDomain(saved);
    }

    @Override
    public boolean existsByCategoryAndName(ProductCategory category, String name) {
        return productJpaRepository.existsByCategoryAndName(category, name);
    }

    private ProductJpaEntity toJpa(Product product) {
        return ProductJpaEntity.builder()
                .id(product.getId())
                .category(product.getCategory())
                .name(product.getName())
                .summary(product.getSummary())
                .description(product.getDescription())
                .thumbnailUrl(product.getThumbnailUrl())
                .published(product.isPublished())
                .build();
    }

    private Product toDomain(ProductJpaEntity entity) {
        return Product.restore(
                entity.getId(),
                entity.getCategory(),
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
