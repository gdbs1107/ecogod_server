package or.ecogod.ecogod.infra.persistence.inquiry.repository;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.domain.inquiry.domain.model.QuoteInquiry;
import or.ecogod.ecogod.domain.inquiry.repository.QuoteInquiryRepository;
import or.ecogod.ecogod.infra.persistence.inquiry.entity.QuoteInquiryJpaEntity;
import or.ecogod.ecogod.infra.persistence.inquiry.repository.jpa.QuoteInquiryJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuoteInquiryRepositoryImpl implements QuoteInquiryRepository {

    private final QuoteInquiryJpaRepository quoteInquiryJpaRepository;

    @Override
    public QuoteInquiry save(QuoteInquiry quoteInquiry) {
        QuoteInquiryJpaEntity saved = quoteInquiryJpaRepository.save(toJpa(quoteInquiry));
        return toDomain(saved);
    }

    private QuoteInquiryJpaEntity toJpa(QuoteInquiry quoteInquiry) {
        return QuoteInquiryJpaEntity.builder()
                .id(quoteInquiry.getId())
                .name(quoteInquiry.getName())
                .companyName(quoteInquiry.getCompanyName())
                .phone(quoteInquiry.getPhone())
                .email(quoteInquiry.getEmail())
                .message(quoteInquiry.getMessage())
                .privacyAgreed(quoteInquiry.isPrivacyAgreed())
                .status(quoteInquiry.getStatus())
                .build();
    }

    private QuoteInquiry toDomain(QuoteInquiryJpaEntity entity) {
        return QuoteInquiry.restore(
                entity.getId(),
                entity.getName(),
                entity.getCompanyName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getMessage(),
                entity.isPrivacyAgreed(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
