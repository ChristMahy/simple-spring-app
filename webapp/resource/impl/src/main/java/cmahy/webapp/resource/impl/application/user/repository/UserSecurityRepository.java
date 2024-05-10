package cmahy.webapp.resource.impl.application.user.repository;

import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.domain.user.UserSecurity;

import java.util.Optional;

public interface UserSecurityRepository {

    Optional<UserSecurity> findByUserNameAndAuthProvider(String username, AuthProvider authProvider);

    UserSecurity save(UserSecurity userSecurity);
}
