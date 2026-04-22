package or.ecogad.ecogad.api.inquiry.response;

import or.ecogad.ecogad.domain.inquiry.application.result.InquiryCreateResult;

import java.time.LocalDateTime;

public record InquiryCreateResponse(
        Long id,
        String status,
        LocalDateTime createdAt
) {
    public static InquiryCreateResponse from(InquiryCreateResult result) {
        return new InquiryCreateResponse(result.id(), result.status(), result.createdAt());
    }
}
