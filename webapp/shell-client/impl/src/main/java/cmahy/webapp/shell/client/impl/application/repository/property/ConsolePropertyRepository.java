package cmahy.webapp.shell.client.impl.application.repository.property;

import cmahy.webapp.shell.client.impl.application.vo.property.console.ConsolePropertyVo;

import java.util.Optional;

public interface ConsolePropertyRepository {

    Optional<ConsolePropertyVo> consoleProperty();

    Optional<String> findFormat();
}
