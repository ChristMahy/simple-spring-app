package cmahy.webapp.resource.impl.adapter.user.repository;

import cmahy.webapp.resource.impl.adapter.user.domain.UserSecurity;
import cmahy.webapp.resource.impl.application.user.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryImpl extends UserRepository<UserSecurity>, JpaRepository<UserSecurity, Long> {
}
