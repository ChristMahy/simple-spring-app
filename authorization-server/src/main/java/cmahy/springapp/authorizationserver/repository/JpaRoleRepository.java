package cmahy.springapp.authorizationserver.repository;

import cmahy.springapp.authorizationserver.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoleRepository extends RoleRepository, CrudRepository<Role, Long> {
}
