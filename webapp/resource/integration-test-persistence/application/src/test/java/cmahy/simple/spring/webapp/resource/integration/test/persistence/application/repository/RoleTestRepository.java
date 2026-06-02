package cmahy.simple.spring.webapp.resource.integration.test.persistence.application.repository;

import cmahy.simple.spring.webapp.user.kernel.domain.Role;

public interface RoleTestRepository<R extends Role> {

    R save(R role);

}
