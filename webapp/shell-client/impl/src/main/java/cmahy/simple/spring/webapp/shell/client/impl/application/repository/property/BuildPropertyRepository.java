package cmahy.simple.spring.webapp.shell.client.impl.application.repository.property;

import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.BuildPropertyVo;

import java.util.Optional;

public interface BuildPropertyRepository {

    Optional<BuildPropertyVo> buildProperty();
}
