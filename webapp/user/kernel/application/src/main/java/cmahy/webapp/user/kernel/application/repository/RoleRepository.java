package cmahy.simple.spring.webapp.user.kernel.application.repository;

import cmahy.simple.spring.webapp.user.kernel.domain.Role;

import java.util.Optional;

public interface RoleRepository<R extends Role> {

    Optional<R> findByName(String name);

    R save(R role);
}
