package cmahy.simple.spring.webapp.shell.client.impl.application.repository.property;

import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.help.HelpPropertyVo;

import java.util.Optional;

public interface HelpPropertyRepository {

    Optional<HelpPropertyVo> helpProperty();
}
