package cmahy.simple.spring.webapp.resource.integration.test.persistence.h2.repository;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.application.repository.RoleTestRepository;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleJpaTestRepository extends
    JpaRepository<JpaRole, UUID>,
    RoleTestRepository<JpaRole> {
}
