package or.ecogod.ecogod.domain.inquiry.port;

import or.ecogod.ecogod.domain.inquiry.domain.model.QuoteInquiry;

public interface InquiryNotificationSender {
    void send(QuoteInquiry inquiry);
}
