package cmahy.webapp.user.adapter.jpa.repository;

import cmahy.webapp.user.adapter.jpa.entity.domain.JpaUserSecurity;
import cmahy.webapp.user.kernel.application.repository.UserSecurityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSecurityRepositoryImpl extends UserSecurityRepository<JpaUserSecurity>, JpaRepository<JpaUserSecurity, UUID> {
}
