package cmahy.webapp.shell.client.impl.adapter.user.repository;

import cmahy.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.webapp.user.kernel.domain.Role;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class RoleRepositoryImpl implements RoleRepository {

    @Override
    public Optional<Role> findByName(String name) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public Role save(Role role) {
        throw new IllegalStateException("Not yet implemented !");
    }
}
