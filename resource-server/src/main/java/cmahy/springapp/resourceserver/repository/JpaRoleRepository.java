package cmahy.springapp.resourceserver.repository;

import cmahy.springapp.resourceserver.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoleRepository extends RoleRepository, CrudRepository<Role, Long> {
}
