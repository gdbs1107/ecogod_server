package or.ecogod.ecogod.infra.persistence.product.repository.jpa;

import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;
import or.ecogod.ecogod.infra.persistence.product.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
    boolean existsByCategoryAndName(ProductCategory category, String name);
}
