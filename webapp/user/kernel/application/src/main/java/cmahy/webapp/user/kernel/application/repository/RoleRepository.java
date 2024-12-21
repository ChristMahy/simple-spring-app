package cmahy.webapp.user.kernel.application.repository;

import cmahy.webapp.user.kernel.domain.Role;

import java.util.Optional;

public interface RoleRepository<R extends Role> {

    Optional<R> findByName(String name);

    R save(R role);
}
