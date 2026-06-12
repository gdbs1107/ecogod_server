package or.ecogod.ecogod.api.notice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.api.notice.request.AdminNoticeCreateRequest;
import or.ecogod.ecogod.api.notice.request.AdminNoticeUpdateRequest;
import or.ecogod.ecogod.api.notice.response.NoticeResponse;
import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.domain.notice.application.service.AdminNoticeService;
import or.ecogod.ecogod.domain.notice.domain.model.Notice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/notices")
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NoticeResponse>>> getNotices() {
        return ResponseEntity.ok(ApiResponse.success(
                adminNoticeService.getAdminNotices().stream().map(NoticeResponse::from).toList()
        ));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeResponse>> getNotice(@PathVariable Long noticeId) {
        return ResponseEntity.ok(ApiResponse.success(NoticeResponse.from(adminNoticeService.getAdminNotice(noticeId))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NoticeResponse>> createNotice(@Valid @RequestBody AdminNoticeCreateRequest request) {
        Notice saved = adminNoticeService.createNotice(
                request.title(),
                request.summary(),
                request.content(),
                request.published()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(NoticeResponse.from(saved)));
    }

    @PatchMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeResponse>> updateNotice(
            @PathVariable Long noticeId,
            @Valid @RequestBody AdminNoticeUpdateRequest request
    ) {
        Notice updated = adminNoticeService.updateNotice(
                noticeId,
                request.title(),
                request.summary(),
                request.content(),
                request.published()
        );
        return ResponseEntity.ok(ApiResponse.success(NoticeResponse.from(updated)));
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<Void>> deleteNotice(@PathVariable Long noticeId) {
        adminNoticeService.deleteNotice(noticeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
