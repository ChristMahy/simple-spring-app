package cmahy.springapp.authorizationserver.repository;

import cmahy.springapp.authorizationserver.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}
