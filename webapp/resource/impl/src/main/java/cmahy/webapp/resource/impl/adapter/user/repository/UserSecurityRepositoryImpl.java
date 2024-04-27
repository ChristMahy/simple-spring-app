package cmahy.webapp.resource.impl.adapter.user.repository;

import cmahy.webapp.resource.impl.application.user.repository.UserSecurityRepository;
import cmahy.webapp.resource.impl.domain.user.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityRepositoryImpl extends UserSecurityRepository, JpaRepository<UserSecurity, Long> {
}
