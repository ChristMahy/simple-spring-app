package cmahy.webapp.resource.impl.adapter.user.repository;

import cmahy.webapp.resource.impl.application.user.repository.RoleRepository;
import cmahy.webapp.resource.impl.domain.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositoryImpl extends RoleRepository, JpaRepository<Role, Long> {
}
