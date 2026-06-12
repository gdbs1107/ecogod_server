package or.ecogod.ecogod.api.notice;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.api.notice.response.NoticeResponse;
import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.domain.notice.application.service.PublicNoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
public class PublicNoticeController {

    private final PublicNoticeService publicNoticeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NoticeResponse>>> getNotices() {
        return ResponseEntity.ok(ApiResponse.success(
                publicNoticeService.getNotices().stream().map(NoticeResponse::from).toList()
        ));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeResponse>> getNotice(@PathVariable Long noticeId) {
        return ResponseEntity.ok(ApiResponse.success(NoticeResponse.from(publicNoticeService.getNotice(noticeId))));
    }
}
