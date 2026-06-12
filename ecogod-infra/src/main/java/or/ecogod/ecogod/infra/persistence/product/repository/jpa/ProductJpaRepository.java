package or.ecogod.ecogod.infra.persistence.product.repository.jpa;

import or.ecogod.ecogod.infra.persistence.product.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
    boolean existsByCategoryIdAndNameIgnoreCase(Long categoryId, String name);

    boolean existsByCategoryIdAndNameIgnoreCaseAndIdNot(Long categoryId, String name, Long id);

    boolean existsByCategoryId(Long categoryId);

    List<ProductJpaEntity> findAllByOrderByUpdatedAtDesc();

    List<ProductJpaEntity> findByPublishedTrueAndCategory_SlugOrderByUpdatedAtDesc(String categorySlug);
}
