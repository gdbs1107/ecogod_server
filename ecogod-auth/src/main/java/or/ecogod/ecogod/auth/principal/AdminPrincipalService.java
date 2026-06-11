package or.ecogod.ecogod.auth.principal;

import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import or.ecogod.ecogod.domain.auth.repository.AdminUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AdminPrincipalService implements UserDetailsService {

    private final AdminUserRepository adminUserRepository;

    public AdminPrincipalService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminUserRepository.findByLoginId(username)
                .map(AdminPrincipal::new)
                .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_USER_NOT_FOUND));
    }

    public AdminPrincipal loadUserById(Long adminId) {
        return adminUserRepository.findById(adminId)
                .map(AdminPrincipal::new)
                .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_USER_NOT_FOUND));
    }
}
