package or.ecogad.ecogad.domain.product.domain.model;

import or.ecogad.ecogad.common.exception.CustomException;
import or.ecogad.ecogad.common.exception.ErrorCode;

public enum ProductCategory {
    ULPA,
    HEPA,
    MEDIUM,
    PRE,
    CHEMICAL,
    CLEAN_EQUIPMENT,
    CLEANROOM_MAINTENANCE,
    ETC;

    public static ProductCategory from(String value) {
        try {
            return ProductCategory.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new CustomException(ErrorCode.INVALID_PRODUCT_CATEGORY);
        }
    }
}
