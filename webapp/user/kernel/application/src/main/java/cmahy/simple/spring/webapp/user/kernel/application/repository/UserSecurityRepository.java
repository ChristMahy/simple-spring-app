package cmahy.simple.spring.webapp.user.kernel.application.repository;

import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.UserSecurity;

import java.util.Optional;

public interface UserSecurityRepository<US extends UserSecurity> {

    Optional<US> findByUserNameAndAuthProvider(String username, AuthProvider authProvider);

    US save(US userSecurity);
}
