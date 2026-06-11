package or.ecogod.ecogod.domain.product.repository;

import or.ecogod.ecogod.domain.product.domain.model.Product;
import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;

public interface ProductRepository {
    Product save(Product product);

    boolean existsByCategoryAndName(ProductCategory category, String name);
}
