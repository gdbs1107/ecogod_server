package or.ecogod.ecogod.api.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.api.auth.request.AdminLoginRequest;
import or.ecogod.ecogod.api.auth.request.AdminSignupRequest;
import or.ecogod.ecogod.api.auth.response.AdminLoginResponse;
import or.ecogod.ecogod.api.auth.response.AdminProfileResponse;
import or.ecogod.ecogod.auth.principal.AdminPrincipal;
import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.domain.auth.application.result.AdminLoginResult;
import or.ecogod.ecogod.domain.auth.application.result.AdminProfileResult;
import or.ecogod.ecogod.domain.auth.application.service.AdminAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin Auth", description = "어드민 로그인/인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AdminAuthService adminAuthService;

    @Operation(summary = "어드민 회원가입", description = "어드민 계정을 생성합니다. 회원가입 View는 제공하지 않습니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AdminProfileResponse>> signup(
            @Valid @RequestBody AdminSignupRequest request
    ) {
        AdminProfileResult result = adminAuthService.signup(request.toCommand());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(AdminProfileResponse.from(result)));
    }

    @Operation(summary = "어드민 로그인", description = "로그인 성공 시 access token을 반환합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AdminLoginResponse>> login(
            @Valid @RequestBody AdminLoginRequest request
    ) {
        AdminLoginResult result = adminAuthService.login(request.toCommand());
        return ResponseEntity.ok(ApiResponse.success(AdminLoginResponse.from(result)));
    }

    @Operation(summary = "현재 로그인한 어드민 조회", description = "Bearer access token 기준으로 현재 관리자 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AdminProfileResponse>> me(
            @AuthenticationPrincipal AdminPrincipal principal
    ) {
        AdminProfileResult result = adminAuthService.getAuthenticatedAdmin(principal.getAdminId());
        return ResponseEntity.ok(ApiResponse.success(AdminProfileResponse.from(result)));
    }
}
