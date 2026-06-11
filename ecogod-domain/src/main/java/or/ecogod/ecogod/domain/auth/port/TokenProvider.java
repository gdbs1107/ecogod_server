package or.ecogod.ecogod.domain.auth.port;

import or.ecogod.ecogod.domain.auth.domain.model.AdminUser;

public interface TokenProvider {
    String createAccessToken(AdminUser adminUser);
}
