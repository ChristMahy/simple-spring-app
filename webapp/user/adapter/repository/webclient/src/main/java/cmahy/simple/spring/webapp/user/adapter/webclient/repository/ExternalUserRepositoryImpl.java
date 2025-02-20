package cmahy.simple.spring.webapp.user.adapter.webclient.repository;

import cmahy.simple.spring.webapp.user.adapter.webclient.entity.domain.ExternalUser;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class ExternalUserRepositoryImpl implements UserRepository<ExternalUser> {

    @Override
    public Optional<ExternalUser> findById(UserId id) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public Optional<ExternalUser> findByUserName(String username) {
        throw new IllegalStateException("Not yet implemented !");
    }
}
