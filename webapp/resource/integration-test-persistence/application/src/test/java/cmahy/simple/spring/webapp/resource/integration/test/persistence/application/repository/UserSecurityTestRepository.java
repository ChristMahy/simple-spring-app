package cmahy.simple.spring.webapp.resource.integration.test.persistence.application.repository;

import cmahy.simple.spring.webapp.user.kernel.domain.UserSecurity;

public interface UserSecurityTestRepository<U extends UserSecurity> {

    U save(U user);

}
