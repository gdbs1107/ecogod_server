package or.ecogod.ecogod.domain.inquiry.application.result;

import java.time.LocalDateTime;

public record InquiryCreateResult(
        Long id,
        String status,
        LocalDateTime createdAt
) {
}
