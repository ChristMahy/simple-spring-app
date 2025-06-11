package cmahy.simple.spring.webapp.user.adapter.webclient.repository;

import cmahy.simple.spring.webapp.user.adapter.webclient.entity.domain.ExternalRight;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class ExternalRightRepositoryImpl implements RightRepository<ExternalRight> {

    @Override
    public Optional<ExternalRight> findByName(String name) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public ExternalRight save(ExternalRight right) {
        throw new IllegalStateException("Not yet implemented !");
    }

}
