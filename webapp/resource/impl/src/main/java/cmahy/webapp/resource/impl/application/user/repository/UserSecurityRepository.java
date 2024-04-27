package cmahy.webapp.resource.impl.application.user.repository;

import cmahy.webapp.resource.impl.domain.user.UserSecurity;

import java.util.Optional;

public interface UserSecurityRepository {

    Optional<UserSecurity> findByUserName(String username);

    UserSecurity save(UserSecurity userSecurity);
}
