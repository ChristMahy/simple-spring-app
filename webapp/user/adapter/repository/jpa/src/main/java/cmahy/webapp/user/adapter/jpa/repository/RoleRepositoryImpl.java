package cmahy.webapp.user.adapter.jpa.repository;

import cmahy.webapp.user.adapter.jpa.entity.JpaRole;
import cmahy.webapp.user.kernel.application.repository.RoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositoryImpl extends RoleRepository<JpaRole>, JpaRepository<JpaRole, Long> {
}
