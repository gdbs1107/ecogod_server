package or.ecogad.ecogad.api.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import or.ecogad.ecogad.domain.auth.application.command.AdminLoginCommand;

public record AdminLoginRequest(
        @NotBlank(message = "로그인 ID는 필수입니다.")
        @Size(max = 50, message = "로그인 ID는 50자 이하여야 합니다.")
        String loginId,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(max = 100, message = "비밀번호는 100자 이하여야 합니다.")
        String password
) {
    public AdminLoginCommand toCommand() {
        return new AdminLoginCommand(loginId, password);
    }
}
