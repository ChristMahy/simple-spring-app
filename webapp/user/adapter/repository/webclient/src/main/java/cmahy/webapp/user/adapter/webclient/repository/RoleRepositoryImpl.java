package cmahy.webapp.user.adapter.webclient.repository;

import cmahy.webapp.user.adapter.webclient.entity.ExternalRole;
import cmahy.webapp.user.kernel.application.repository.RoleRepository;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class RoleRepositoryImpl implements RoleRepository<ExternalRole> {

    @Override
    public Optional<ExternalRole> findByName(String name) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public ExternalRole save(ExternalRole role) {
        throw new IllegalStateException("Not yet implemented !");
    }
}
