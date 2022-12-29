package cmahy.springapp.repository;

import cmahy.springapp.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);

    User save(User user);
}
