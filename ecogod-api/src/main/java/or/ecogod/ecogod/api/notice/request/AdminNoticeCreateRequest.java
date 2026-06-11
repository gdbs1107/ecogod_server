package or.ecogod.ecogod.api.notice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import or.ecogod.ecogod.domain.notice.application.command.NoticeCreateCommand;

public record AdminNoticeCreateRequest(
        @NotBlank(message = "공지 제목은 필수입니다.")
        @Size(max = 200, message = "공지 제목은 200자 이하여야 합니다.")
        String title,

        @NotBlank(message = "공지 내용은 필수입니다.")
        String content,

        boolean published
) {
    public NoticeCreateCommand toCommand() {
        return new NoticeCreateCommand(title, content, published);
    }
}
