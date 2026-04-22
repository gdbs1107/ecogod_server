package or.ecogad.ecogad.api.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import or.ecogad.ecogad.domain.auth.application.command.AdminSignupCommand;

public record AdminSignupRequest(
        @NotBlank(message = "로그인 ID는 필수입니다.")
        @Size(min = 4, max = 50, message = "로그인 ID는 4자 이상 50자 이하여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "로그인 ID는 영문, 숫자, ., _, - 만 사용할 수 있습니다.")
        String loginId,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 100, message = "비밀번호는 8자 이상 100자 이하여야 합니다.")
        String password,

        @NotBlank(message = "이름은 필수입니다.")
        @Size(max = 100, message = "이름은 100자 이하여야 합니다.")
        String name
) {
    public AdminSignupCommand toCommand() {
        return new AdminSignupCommand(loginId, password, name);
    }
}
