package or.ecogod.ecogod.api.inquiry;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.api.inquiry.request.InquiryCreateRequest;
import or.ecogod.ecogod.api.inquiry.response.InquiryCreateResponse;
import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.domain.inquiry.application.result.InquiryCreateResult;
import or.ecogod.ecogod.domain.inquiry.application.service.InquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<ApiResponse<InquiryCreateResponse>> createInquiry(
            @Valid @RequestBody InquiryCreateRequest request
    ) {
        InquiryCreateResult result = inquiryService.createInquiry(request.toCommand());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(InquiryCreateResponse.from(result)));
    }
}
