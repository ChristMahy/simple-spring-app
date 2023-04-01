package cmahy.springapp.resourceserver.repository;

import cmahy.springapp.resourceserver.domain.Role;

import java.util.Optional;

public interface RoleRepository {
    Role save(Role role);

    Optional<Role> findByName(String name);
}
