package cmahy.webapp.user.kernel.application.repository;

import cmahy.webapp.user.kernel.domain.AuthProvider;
import cmahy.webapp.user.kernel.domain.UserSecurity;

import java.util.Optional;

public interface UserSecurityRepository {

    Optional<UserSecurity> findByUserNameAndAuthProvider(String username, AuthProvider authProvider);

    UserSecurity save(UserSecurity userSecurity);
}
