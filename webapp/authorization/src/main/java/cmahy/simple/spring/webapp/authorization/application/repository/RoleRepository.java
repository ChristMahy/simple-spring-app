package cmahy.simple.spring.webapp.authorization.application.repository;

import cmahy.simple.spring.webapp.authorization.domain.Role;

import java.util.Optional;

public interface RoleRepository {

    Role save(Role role);

    Optional<Role> findByName(String name);
}
