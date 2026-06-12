package or.ecogod.ecogod.domain.product.repository;

import or.ecogod.ecogod.domain.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);

    boolean existsByCategoryIdAndName(Long categoryId, String name);

    boolean existsByCategoryIdAndNameAndIdNot(Long categoryId, String name, Long productId);

    boolean existsByCategoryId(Long categoryId);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findPublishedByCategorySlug(String categorySlug);

    void deleteById(Long id);
}
