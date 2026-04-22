package or.ecogad.ecogad.auth.principal;

import or.ecogad.ecogad.common.exception.CustomException;
import or.ecogad.ecogad.common.exception.ErrorCode;
import or.ecogad.ecogad.domain.auth.repository.AdminUserRepository;
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
