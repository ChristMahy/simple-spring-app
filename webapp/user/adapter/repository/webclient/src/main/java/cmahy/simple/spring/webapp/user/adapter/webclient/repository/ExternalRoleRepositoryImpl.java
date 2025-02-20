package cmahy.simple.spring.webapp.user.adapter.webclient.repository;

import cmahy.simple.spring.webapp.user.adapter.webclient.entity.domain.ExternalRole;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class ExternalRoleRepositoryImpl implements RoleRepository<ExternalRole> {

    @Override
    public Optional<ExternalRole> findByName(String name) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public ExternalRole save(ExternalRole role) {
        throw new IllegalStateException("Not yet implemented !");
    }
}
