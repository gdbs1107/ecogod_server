package or.ecogod.ecogod.api.notice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.api.notice.request.AdminNoticeCreateRequest;
import or.ecogod.ecogod.api.notice.response.AdminNoticeCreateResponse;
import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.domain.notice.application.result.NoticeCreateResult;
import or.ecogod.ecogod.domain.notice.application.service.AdminNoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/notices")
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;

    @PostMapping
    public ResponseEntity<ApiResponse<AdminNoticeCreateResponse>> createNotice(
            @Valid @RequestBody AdminNoticeCreateRequest request
    ) {
        NoticeCreateResult result = adminNoticeService.createNotice(request.toCommand());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(AdminNoticeCreateResponse.from(result)));
    }
}
