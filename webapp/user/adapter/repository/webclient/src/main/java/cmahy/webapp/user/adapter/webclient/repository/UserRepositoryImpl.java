package cmahy.webapp.user.adapter.webclient.repository;

import cmahy.webapp.user.adapter.webclient.entity.ExternalUser;
import cmahy.webapp.user.kernel.application.repository.UserRepository;
import cmahy.webapp.user.kernel.domain.id.UserId;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class UserRepositoryImpl implements UserRepository<ExternalUser> {

    @Override
    public Optional<ExternalUser> findById(UserId id) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public Optional<ExternalUser> findByUserName(String username) {
        throw new IllegalStateException("Not yet implemented !");
    }
}
