package or.ecogod.ecogod.infra.persistence.inquiry.repository.jpa;

import or.ecogod.ecogod.infra.persistence.inquiry.entity.QuoteInquiryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteInquiryJpaRepository extends JpaRepository<QuoteInquiryJpaEntity, Long> {
}
