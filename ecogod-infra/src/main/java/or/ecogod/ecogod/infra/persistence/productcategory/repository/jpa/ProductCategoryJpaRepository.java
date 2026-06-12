package or.ecogod.ecogod.infra.persistence.productcategory.repository.jpa;

import or.ecogod.ecogod.infra.persistence.productcategory.entity.ProductCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategoryJpaEntity, Long> {
    Optional<ProductCategoryJpaEntity> findByCode(String code);

    Optional<ProductCategoryJpaEntity> findBySlug(String slug);

    boolean existsByCode(String code);

    boolean existsBySlug(String slug);

    List<ProductCategoryJpaEntity> findAllByOrderBySortOrderAscNameAsc();

    List<ProductCategoryJpaEntity> findByActiveTrueOrderBySortOrderAscNameAsc();
}
