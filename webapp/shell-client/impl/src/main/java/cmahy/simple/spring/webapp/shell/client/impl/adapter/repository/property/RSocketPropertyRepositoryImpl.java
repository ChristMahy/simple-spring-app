package cmahy.simple.spring.webapp.shell.client.impl.adapter.repository.property;

import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.shell.client.impl.application.repository.property.RSocketPropertyRepository;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.rsocket.RSocketPropertyVo;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class RSocketPropertyRepositoryImpl implements RSocketPropertyRepository {

    private final Optional<RSocketPropertyVo> rSocketPropertyVo;

    public RSocketPropertyRepositoryImpl(ApplicationProperties applicationProperties) {
        rSocketPropertyVo = Optional.ofNullable(applicationProperties)
            .map(ApplicationProperties::rSocket);
    }

    @Override
    public Optional<RSocketPropertyVo> rSocketProperty() {
        return rSocketPropertyVo;
    }

}
