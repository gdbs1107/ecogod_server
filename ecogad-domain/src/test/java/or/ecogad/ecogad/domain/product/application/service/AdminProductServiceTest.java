package or.ecogad.ecogad.domain.product.application.service;

import or.ecogad.ecogad.common.exception.CustomException;
import or.ecogad.ecogad.domain.product.application.command.ProductCreateCommand;
import or.ecogad.ecogad.domain.product.domain.model.Product;
import or.ecogad.ecogad.domain.product.domain.model.ProductCategory;
import or.ecogad.ecogad.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private AdminProductService adminProductService;

    @Test
    @DisplayName("createProduct()는 제품을 성공적으로 등록한다")
    void createProduct_success() {
        // given
        ProductCreateCommand command = new ProductCreateCommand(
                ProductCategory.ULPA,
                "초정밀 ULPA 필터",
                "테스트 요약",
                "테스트 설명",
                "https://example.com/filter.jpg",
                true
        );

        when(productRepository.existsByCategoryAndName(ProductCategory.ULPA, "초정밀 ULPA 필터")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        adminProductService.createProduct(command);

        // then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("createProduct()는 중복 제품일 때 예외를 발생시킨다")
    void createProduct_duplicate_throwsException() {
        // given
        ProductCreateCommand command = new ProductCreateCommand(
                ProductCategory.ULPA,
                "초정밀 ULPA 필터",
                "테스트 요약",
                "테스트 설명",
                null,
                false
        );

        when(productRepository.existsByCategoryAndName(ProductCategory.ULPA, "초정밀 ULPA 필터")).thenReturn(true);

        // when // then
        assertThrows(CustomException.class, () -> adminProductService.createProduct(command));
        verify(productRepository, never()).save(any(Product.class));
    }
}
