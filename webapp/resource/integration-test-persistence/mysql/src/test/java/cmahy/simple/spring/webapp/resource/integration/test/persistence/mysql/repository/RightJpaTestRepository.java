package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.repository;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.application.repository.RightTestRepository;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RightJpaTestRepository extends
    JpaRepository<JpaRight, UUID>,
    RightTestRepository<JpaRight> {
}
