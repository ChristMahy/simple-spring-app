package cmahy.webapp.resource.impl.adapter.user.repository;

import cmahy.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.webapp.user.kernel.domain.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityRepositoryImpl extends UserSecurityRepository, JpaRepository<UserSecurity, Long> {
}
