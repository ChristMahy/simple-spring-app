package cmahy.simple.spring.webapp.shell.client.impl.application.repository.property;

import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.rsocket.RSocketPropertyVo;

import java.util.Optional;

public interface RSocketPropertyRepository {

    Optional<RSocketPropertyVo> rSocketProperty();

}
