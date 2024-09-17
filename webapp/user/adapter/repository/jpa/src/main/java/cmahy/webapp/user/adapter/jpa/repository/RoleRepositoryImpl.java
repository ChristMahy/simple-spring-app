package cmahy.webapp.user.adapter.jpa.repository;

import cmahy.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.webapp.user.kernel.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositoryImpl extends RoleRepository, JpaRepository<Role, Long> {
}
