package or.ecogod.ecogod.domain.auth.application.service;

import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.domain.auth.application.command.AdminLoginCommand;
import or.ecogod.ecogod.domain.auth.application.command.AdminSignupCommand;
import or.ecogod.ecogod.domain.auth.domain.model.AdminUser;
import or.ecogod.ecogod.domain.auth.port.PasswordEncoderPort;
import or.ecogod.ecogod.domain.auth.port.TokenProvider;
import or.ecogod.ecogod.domain.auth.repository.AdminUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminAuthServiceTest {

    @Mock
    private AdminUserRepository adminUserRepository;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AdminAuthService adminAuthService;

    @Test
    @DisplayName("signup()은 관리자 계정을 생성한다")
    void signup_success() {
        // given
        AdminSignupCommand command = new AdminSignupCommand("admin", "password123!", "관리자");
        AdminUser saved = AdminUser.restore(1L, "admin", "encoded", "관리자", or.ecogod.ecogod.domain.auth.domain.model.AdminRole.ADMIN, null, null);

        when(adminUserRepository.existsByLoginId("admin")).thenReturn(false);
        when(passwordEncoderPort.encode("password123!")).thenReturn("encoded");
        when(adminUserRepository.save(any(AdminUser.class))).thenReturn(saved);

        // when
        var result = adminAuthService.signup(command);

        // then
        assertEquals("admin", result.loginId());
        verify(adminUserRepository).save(any(AdminUser.class));
    }

    @Test
    @DisplayName("signup()은 중복 로그인 ID면 예외를 발생시킨다")
    void signup_duplicate_throwsException() {
        // given
        AdminSignupCommand command = new AdminSignupCommand("admin", "password123!", "관리자");
        when(adminUserRepository.existsByLoginId("admin")).thenReturn(true);

        // when // then
        assertThrows(CustomException.class, () -> adminAuthService.signup(command));
        verify(adminUserRepository, never()).save(any(AdminUser.class));
    }

    @Test
    @DisplayName("login()은 올바른 로그인 정보면 access token을 반환한다")
    void login_success() {
        // given
        AdminLoginCommand command = new AdminLoginCommand("admin", "password123!");
        AdminUser adminUser = AdminUser.restore(1L, "admin", "encoded", "관리자", or.ecogod.ecogod.domain.auth.domain.model.AdminRole.ADMIN, null, null);

        when(adminUserRepository.findByLoginId("admin")).thenReturn(Optional.of(adminUser));
        when(passwordEncoderPort.matches("password123!", "encoded")).thenReturn(true);
        when(tokenProvider.createAccessToken(adminUser)).thenReturn("token-value");

        // when
        var result = adminAuthService.login(command);

        // then
        assertEquals("token-value", result.accessToken());
        assertEquals("admin", result.admin().loginId());
    }

    @Test
    @DisplayName("login()은 비밀번호가 틀리면 예외를 발생시킨다")
    void login_invalidPassword_throwsException() {
        // given
        AdminLoginCommand command = new AdminLoginCommand("admin", "wrong");
        AdminUser adminUser = AdminUser.restore(1L, "admin", "encoded", "관리자", or.ecogod.ecogod.domain.auth.domain.model.AdminRole.ADMIN, null, null);

        when(adminUserRepository.findByLoginId("admin")).thenReturn(Optional.of(adminUser));
        when(passwordEncoderPort.matches("wrong", "encoded")).thenReturn(false);

        // when // then
        assertThrows(CustomException.class, () -> adminAuthService.login(command));
    }
}
