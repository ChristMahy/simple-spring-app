package cmahy.webapp.resource.impl.application.user.repository;

import cmahy.webapp.resource.impl.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByUserName(String username);
}
