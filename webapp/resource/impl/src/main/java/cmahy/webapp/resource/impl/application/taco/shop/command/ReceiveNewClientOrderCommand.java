package cmahy.webapp.resource.impl.application.taco.shop.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.resource.impl.application.taco.shop.service.ReceiveAndCreateClientOrder;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import jakarta.inject.Named;

@Command
@Named
public class ReceiveNewClientOrderCommand {

    private final ReceiveAndCreateClientOrder receiveAndCreateClientOrder;

    public ReceiveNewClientOrderCommand(ReceiveAndCreateClientOrder receiveAndCreateClientOrder) {
        this.receiveAndCreateClientOrder = receiveAndCreateClientOrder;
    }

    public ClientOrderOutputAppVo execute(ClientOrderInputAppVo input) {
        return receiveAndCreateClientOrder.execute(input);
    }
}
