package or.ecogod.ecogod.api.inquiry.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import or.ecogod.ecogod.domain.inquiry.application.command.InquiryCreateCommand;

public record InquiryCreateRequest(
        @NotBlank(message = "이름은 필수입니다.")
        @Size(max = 80, message = "이름은 80자 이하여야 합니다.")
        String name,

        @NotBlank(message = "회사명은 필수입니다.")
        @Size(max = 120, message = "회사명은 120자 이하여야 합니다.")
        String companyName,

        @NotBlank(message = "연락처는 필수입니다.")
        @Size(max = 30, message = "연락처는 30자 이하여야 합니다.")
        String phone,

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @Size(max = 120, message = "이메일은 120자 이하여야 합니다.")
        String email,

        @NotBlank(message = "문의내용은 필수입니다.")
        String message,

        @AssertTrue(message = "개인정보 동의가 필요합니다.")
        boolean privacyAgreed
) {
    public InquiryCreateCommand toCommand() {
        return new InquiryCreateCommand(name, companyName, phone, email, message, privacyAgreed);
    }
}
