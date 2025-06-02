package cmahy.simple.spring.webapp.shell.client.impl.adapter.repository.property;

import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.shell.client.impl.application.repository.property.ConsolePropertyRepository;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.console.ConsolePropertyVo;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.console.OutputPropertyVo;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class ConsolePropertyRepositoryImpl implements ConsolePropertyRepository {

    private final Optional<ConsolePropertyVo> consolePropertyVo;

    public ConsolePropertyRepositoryImpl(ApplicationProperties applicationProperties) {
        this.consolePropertyVo = Optional.ofNullable(applicationProperties)
            .map(ApplicationProperties::console);
    }

    @Override
    public Optional<ConsolePropertyVo> consoleProperty() {
        return consolePropertyVo;
    }

    @Override
    public Optional<String> findFormat() {
        return consolePropertyVo
            .map(ConsolePropertyVo::output)
            .map(OutputPropertyVo::format);
    }
}
