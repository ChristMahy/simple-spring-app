package cmahy.simple.spring.webapp.user.adapter.jpa.repository;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRight;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RightRepositoryImpl extends RightRepository<JpaRight>, JpaRepository<JpaRight, UUID> {
}
