package or.ecogad.ecogad.infra.persistence.product.repository.jpa;

import or.ecogad.ecogad.domain.product.domain.model.ProductCategory;
import or.ecogad.ecogad.infra.persistence.product.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
    boolean existsByCategoryAndName(ProductCategory category, String name);
}
