package or.ecogod.ecogod.infra.persistence.productcategory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import or.ecogod.ecogod.infra.persistence.base.BaseJpaEntity;

@Entity
@Getter
@Table(name = "product_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategoryJpaEntity extends BaseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String code;

    @Column(nullable = false, unique = true, length = 80)
    private String slug;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private int sortOrder;

    @Column(nullable = false)
    private boolean active;

    @Builder(toBuilder = true)
    private ProductCategoryJpaEntity(Long id, String code, String slug, String name, String description, int sortOrder, boolean active) {
        this.id = id;
        this.code = code;
        this.slug = slug;
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder;
        this.active = active;
    }
}
