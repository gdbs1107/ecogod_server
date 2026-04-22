package or.ecogad.ecogad.domain.inquiry.application.service;

import lombok.RequiredArgsConstructor;
import or.ecogad.ecogad.common.exception.CustomException;
import or.ecogad.ecogad.common.exception.ErrorCode;
import or.ecogad.ecogad.domain.inquiry.application.command.InquiryCreateCommand;
import or.ecogad.ecogad.domain.inquiry.application.result.InquiryCreateResult;
import or.ecogad.ecogad.domain.inquiry.domain.model.QuoteInquiry;
import or.ecogad.ecogad.domain.inquiry.port.InquiryNotificationSender;
import or.ecogad.ecogad.domain.inquiry.repository.QuoteInquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final QuoteInquiryRepository quoteInquiryRepository;
    private final InquiryNotificationSender inquiryNotificationSender;

    @Transactional
    public InquiryCreateResult createInquiry(InquiryCreateCommand command) {
        QuoteInquiry inquiry = QuoteInquiry.create(
                command.name(),
                command.companyName(),
                command.phone(),
                command.email(),
                command.message(),
                command.privacyAgreed()
        );

        QuoteInquiry saved = quoteInquiryRepository.save(inquiry);

        try {
            inquiryNotificationSender.send(saved);
        } catch (RuntimeException exception) {
            throw new CustomException(ErrorCode.INQUIRY_NOTIFICATION_FAILED);
        }

        return new InquiryCreateResult(saved.getId(), saved.getStatus().name(), saved.getCreatedAt());
    }
}
