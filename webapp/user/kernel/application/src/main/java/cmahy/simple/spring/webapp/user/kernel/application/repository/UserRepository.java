package cmahy.simple.spring.webapp.user.kernel.application.repository;

import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;

import java.util.Optional;

public interface UserRepository<U extends User> {

    Optional<U> findById(UserId id);

    Optional<U> findByUserName(String username);
}
