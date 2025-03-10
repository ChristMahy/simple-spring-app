package cmahy.simple.spring.webapp.authorization.application.repository;

import cmahy.simple.spring.webapp.authorization.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);

    User save(User user);
}
