package or.ecogod.ecogod.domain.product.application.service;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.domain.product.domain.model.Product;
import or.ecogod.ecogod.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicProductService {

    private final ProductRepository productRepository;

    public List<Product> getProductsByCategorySlug(String categorySlug) {
        return productRepository.findPublishedByCategorySlug(categorySlug);
    }
}
