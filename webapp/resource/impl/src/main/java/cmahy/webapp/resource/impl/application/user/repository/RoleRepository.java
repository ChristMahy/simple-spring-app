package cmahy.webapp.resource.impl.application.user.repository;

import cmahy.webapp.resource.impl.domain.user.Role;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(String name);

    Role save(Role role);
}
