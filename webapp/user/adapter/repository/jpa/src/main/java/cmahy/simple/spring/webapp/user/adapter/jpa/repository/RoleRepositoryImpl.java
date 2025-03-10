package cmahy.simple.spring.webapp.user.adapter.jpa.repository;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRole;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepositoryImpl extends RoleRepository<JpaRole>, JpaRepository<JpaRole, UUID> {
}
