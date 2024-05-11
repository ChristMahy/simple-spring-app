package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.id.UserId;

public interface ReceiveAndCreateClientOrder {

    ClientOrderOutputAppVo execute(ClientOrderInputAppVo input, UserId clientId);
}
