package or.ecogod.ecogod.api.notice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminNoticeCreateRequest(
        @NotBlank(message = "공지 제목을 입력해 주세요.")
        String title,
        @NotBlank(message = "공지 요약을 입력해 주세요.")
        String summary,
        @NotBlank(message = "공지 내용을 입력해 주세요.")
        String content,
        @NotNull(message = "공개 여부를 선택해 주세요.")
        Boolean published
) {
}
