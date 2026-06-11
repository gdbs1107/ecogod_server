package or.ecogod.ecogod.infra.persistence.inquiry.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import or.ecogod.ecogod.domain.inquiry.domain.model.InquiryStatus;
import or.ecogod.ecogod.infra.persistence.base.BaseJpaEntity;

@Entity
@Getter
@Table(name = "quote_inquiries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuoteInquiryJpaEntity extends BaseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 120)
    private String companyName;

    @Column(nullable = false, length = 30)
    private String phone;

    @Column(nullable = false, length = 120)
    private String email;

    @Lob
    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean privacyAgreed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InquiryStatus status;

    @Builder(toBuilder = true)
    private QuoteInquiryJpaEntity(
            Long id,
            String name,
            String companyName,
            String phone,
            String email,
            String message,
            boolean privacyAgreed,
            InquiryStatus status
    ) {
        this.id = id;
        this.name = name;
        this.companyName = companyName;
        this.phone = phone;
        this.email = email;
        this.message = message;
        this.privacyAgreed = privacyAgreed;
        this.status = status;
    }
}
