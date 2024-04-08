package cmahy.webapp.resource.impl.application.user.repository;

import cmahy.webapp.resource.impl.domain.user.User;

import java.util.Optional;

public interface UserRepository<T extends User> {

    Optional<T> findById(Long id);
}
