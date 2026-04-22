package or.ecogad.ecogad.domain.inquiry.port;

import or.ecogad.ecogad.domain.inquiry.domain.model.QuoteInquiry;

public interface InquiryNotificationSender {
    void send(QuoteInquiry inquiry);
}
