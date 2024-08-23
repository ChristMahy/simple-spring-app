package cmahy.webapp.shell.client.impl.adapter.user.repository;

import cmahy.webapp.user.kernel.application.repository.UserRepository;
import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.domain.id.UserId;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findById(UserId id) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public Optional<User> findByUserName(String username) {
        throw new IllegalStateException("Not yet implemented !");
    }
}
