package or.ecogod.ecogod.domain.product.application.service;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import or.ecogod.ecogod.domain.product.application.command.ProductCreateCommand;
import or.ecogod.ecogod.domain.product.application.result.ProductCreateResult;
import or.ecogod.ecogod.domain.product.domain.model.Product;
import or.ecogod.ecogod.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductCreateResult createProduct(ProductCreateCommand command) {
        boolean isDuplicate = productRepository.existsByCategoryAndName(command.category(), command.name());
        if (isDuplicate) {
            throw new CustomException(ErrorCode.DUPLICATE_PRODUCT);
        }

        Product saved = productRepository.save(Product.create(
                command.category(),
                command.name(),
                command.summary(),
                command.description(),
                command.thumbnailUrl(),
                command.published()
        ));

        return new ProductCreateResult(
                saved.getId(),
                saved.getCategory().name(),
                saved.getName(),
                saved.getSummary(),
                saved.getDescription(),
                saved.getThumbnailUrl(),
                saved.isPublished(),
                saved.getCreatedAt()
        );
    }
}
