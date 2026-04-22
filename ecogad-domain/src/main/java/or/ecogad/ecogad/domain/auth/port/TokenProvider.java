package or.ecogad.ecogad.domain.auth.port;

import or.ecogad.ecogad.domain.auth.domain.model.AdminUser;

public interface TokenProvider {
    String createAccessToken(AdminUser adminUser);
}
