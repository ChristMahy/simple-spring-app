package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;

public interface ReceiveAndCreateClientOrder {

    ClientOrderOutputAppVo execute(ClientOrderInputAppVo input);
}
