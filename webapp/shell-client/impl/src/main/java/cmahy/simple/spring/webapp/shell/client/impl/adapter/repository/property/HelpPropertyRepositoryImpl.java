package cmahy.simple.spring.webapp.shell.client.impl.adapter.repository.property;

import cmahy.simple.spring.webapp.shell.client.impl.application.repository.property.HelpPropertyRepository;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.help.HelpPropertyVo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

@ConfigurationProperties(prefix = "shell-client")
public class HelpPropertyRepositoryImpl implements HelpPropertyRepository {

    private final Optional<HelpPropertyVo> helpPropertyVo;

    @ConstructorBinding
    public HelpPropertyRepositoryImpl(HelpPropertyVo help) {
        this.helpPropertyVo = Optional.ofNullable(help);
    }

    @Override
    public Optional<HelpPropertyVo> helpProperty() {
        return helpPropertyVo;
    }
}
