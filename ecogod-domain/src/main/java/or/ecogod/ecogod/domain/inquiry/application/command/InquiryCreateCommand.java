package or.ecogod.ecogod.domain.inquiry.application.command;

public record InquiryCreateCommand(
        String name,
        String companyName,
        String phone,
        String email,
        String message,
        boolean privacyAgreed
) {
}
