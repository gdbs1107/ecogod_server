package or.ecogod.ecogod.domain.auth.application.service;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import or.ecogod.ecogod.domain.auth.application.command.AdminLoginCommand;
import or.ecogod.ecogod.domain.auth.application.command.AdminSignupCommand;
import or.ecogod.ecogod.domain.auth.application.result.AdminLoginResult;
import or.ecogod.ecogod.domain.auth.application.result.AdminProfileResult;
import or.ecogod.ecogod.domain.auth.domain.model.AdminUser;
import or.ecogod.ecogod.domain.auth.port.PasswordEncoderPort;
import or.ecogod.ecogod.domain.auth.port.TokenProvider;
import or.ecogod.ecogod.domain.auth.repository.AdminUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenProvider tokenProvider;

    @Transactional
    public AdminProfileResult signup(AdminSignupCommand command) {
        if (adminUserRepository.existsByLoginId(command.loginId())) {
            throw new CustomException(ErrorCode.DUPLICATE_ADMIN_LOGIN_ID);
        }

        AdminUser created = adminUserRepository.save(
                AdminUser.create(
                        command.loginId().trim(),
                        passwordEncoderPort.encode(command.password()),
                        command.name().trim()
                )
        );

        return toProfile(created);
    }

    @Transactional(readOnly = true)
    public AdminLoginResult login(AdminLoginCommand command) {
        AdminUser adminUser = adminUserRepository.findByLoginId(command.loginId().trim())
                .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_LOGIN_FAILED));

        if (!passwordEncoderPort.matches(command.password(), adminUser.getPasswordHash())) {
            throw new CustomException(ErrorCode.ADMIN_LOGIN_FAILED);
        }

        return new AdminLoginResult(tokenProvider.createAccessToken(adminUser), toProfile(adminUser));
    }

    @Transactional(readOnly = true)
    public AdminProfileResult getAuthenticatedAdmin(Long adminId) {
        AdminUser adminUser = adminUserRepository.findById(adminId)
                .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_USER_NOT_FOUND));
        return toProfile(adminUser);
    }

    private AdminProfileResult toProfile(AdminUser adminUser) {
        return new AdminProfileResult(
                adminUser.getId(),
                adminUser.getLoginId(),
                adminUser.getName(),
                adminUser.getRole().name()
        );
    }
}
