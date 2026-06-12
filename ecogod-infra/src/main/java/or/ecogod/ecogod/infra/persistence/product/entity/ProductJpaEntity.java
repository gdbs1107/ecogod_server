package or.ecogod.ecogod.infra.persistence.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import or.ecogod.ecogod.infra.persistence.base.BaseJpaEntity;
import or.ecogod.ecogod.infra.persistence.productcategory.entity.ProductCategoryJpaEntity;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductJpaEntity extends BaseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategoryJpaEntity category;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 255)
    private String summary;

    @Lob
    private String description;

    @Column(length = 500)
    private String thumbnailUrl;

    @Column(nullable = false)
    private boolean published;

    @Builder(toBuilder = true)
    private ProductJpaEntity(
            Long id,
            ProductCategoryJpaEntity category,
            String name,
            String summary,
            String description,
            String thumbnailUrl,
            boolean published
    ) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.published = published;
    }
}
