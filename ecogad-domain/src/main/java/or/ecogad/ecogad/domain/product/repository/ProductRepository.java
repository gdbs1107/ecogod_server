package or.ecogad.ecogad.domain.product.repository;

import or.ecogad.ecogad.domain.product.domain.model.Product;
import or.ecogad.ecogad.domain.product.domain.model.ProductCategory;

public interface ProductRepository {
    Product save(Product product);

    boolean existsByCategoryAndName(ProductCategory category, String name);
}
