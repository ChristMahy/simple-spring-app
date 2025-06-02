package cmahy.simple.spring.webapp.authorization.adapter.repository;

import cmahy.simple.spring.webapp.authorization.application.repository.RightRepository;
import cmahy.simple.spring.webapp.authorization.domain.Right;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaRightRepository extends
    RightRepository,
    org.springframework.data.repository.Repository<Right, UUID> {
}
