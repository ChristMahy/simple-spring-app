package cmahy.simple.spring.webapp.authorization.adapter.repository;

import cmahy.simple.spring.webapp.authorization.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.authorization.domain.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoleRepository extends
    RoleRepository,
    org.springframework.data.repository.Repository<Role, Long> {
}
