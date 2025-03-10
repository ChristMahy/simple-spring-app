package cmahy.simple.spring.webapp.authorization.adapter.repository;

import cmahy.simple.spring.webapp.authorization.application.repository.UserRepository;
import cmahy.simple.spring.webapp.authorization.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends
    UserRepository,
    org.springframework.data.repository.Repository<User, Long> {
}
