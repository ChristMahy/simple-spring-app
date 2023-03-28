package cmahy.springapp.resourceserver.repository;

import cmahy.springapp.resourceserver.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User save(User user);
}
