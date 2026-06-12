package or.ecogod.ecogod.domain.product.application.service;

import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.domain.product.domain.model.Product;
import or.ecogod.ecogod.domain.product.domain.model.ProductCategory;
import or.ecogod.ecogod.domain.product.repository.ProductCategoryRepository;
import or.ecogod.ecogod.domain.product.repository.ProductRepository;
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

    private static final ProductCategory ULPA_CATEGORY =
            ProductCategory.restore(1L, "ULPA", "ulpa", "ULPA 필터", "초고성능 필터", 10, true, null, null);

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private AdminProductService adminProductService;

    @Test
    @DisplayName("createProduct()는 제품을 성공적으로 등록한다")
    void createProduct_success() {
        // given
        when(productCategoryRepository.findByCode("ULPA")).thenReturn(java.util.Optional.of(ULPA_CATEGORY));
        when(productRepository.existsByCategoryIdAndName(1L, "초정밀 ULPA 필터")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        adminProductService.createProduct(
                "ULPA",
                "초정밀 ULPA 필터",
                "테스트 요약",
                "테스트 설명",
                "https://example.com/filter.jpg",
                true
        );

        // then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("createProduct()는 중복 제품일 때 예외를 발생시킨다")
    void createProduct_duplicate_throwsException() {
        // given
        when(productCategoryRepository.findByCode("ULPA")).thenReturn(java.util.Optional.of(ULPA_CATEGORY));
        when(productRepository.existsByCategoryIdAndName(1L, "초정밀 ULPA 필터")).thenReturn(true);

        // when // then
        assertThrows(
                CustomException.class,
                () -> adminProductService.createProduct(
                        "ULPA",
                        "초정밀 ULPA 필터",
                        "테스트 요약",
                        "테스트 설명",
                        null,
                        false
                )
        );
        verify(productRepository, never()).save(any(Product.class));
    }
}
