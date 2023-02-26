package cmahy.springapp.authorizationserver.repository;

import cmahy.springapp.authorizationserver.domain.Role;

public interface RoleRepository {
    Role save(Role role);
}
