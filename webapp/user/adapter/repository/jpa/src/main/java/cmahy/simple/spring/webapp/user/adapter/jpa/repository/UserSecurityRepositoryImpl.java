package cmahy.simple.spring.webapp.user.adapter.jpa.repository;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaUserSecurity;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserSecurityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSecurityRepositoryImpl extends UserSecurityRepository<JpaUserSecurity>, JpaRepository<JpaUserSecurity, UUID> {
}
