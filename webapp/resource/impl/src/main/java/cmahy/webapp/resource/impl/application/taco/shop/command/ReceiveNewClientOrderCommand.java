package cmahy.webapp.resource.impl.application.taco.shop.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.resource.impl.application.taco.shop.service.ReceiveAndCreateClientOrder;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.taco.shop.vo.input.ClientOrderInputVo;
import cmahy.webapp.resource.taco.shop.vo.output.ClientOrderOutputVo;
import jakarta.inject.Named;

@Command
@Named
public class ReceiveNewClientOrderCommand {

    private final ReceiveAndCreateClientOrder receiveAndCreateClientOrder;

    public ReceiveNewClientOrderCommand(ReceiveAndCreateClientOrder receiveAndCreateClientOrder) {
        this.receiveAndCreateClientOrder = receiveAndCreateClientOrder;
    }

    public ClientOrderOutputVo execute(ClientOrderInputVo input, UserId clientId) {
        return receiveAndCreateClientOrder.execute(input, clientId);
    }
}
