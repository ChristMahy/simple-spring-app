package cmahy.webapp.shell.client.impl.adapter.repository.property;

import cmahy.webapp.shell.client.impl.application.repository.property.ConsolePropertyRepository;
import cmahy.webapp.shell.client.impl.application.vo.property.console.ConsolePropertyVo;
import cmahy.webapp.shell.client.impl.application.vo.property.console.OutputPropertyVo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

@ConfigurationProperties(prefix = "application")
public class ConsolePropertyRepositoryImpl implements ConsolePropertyRepository {

    private final Optional<ConsolePropertyVo> consolePropertyVo;

    @ConstructorBinding
    public ConsolePropertyRepositoryImpl(ConsolePropertyVo console) {
        this.consolePropertyVo = Optional.ofNullable(console);
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
