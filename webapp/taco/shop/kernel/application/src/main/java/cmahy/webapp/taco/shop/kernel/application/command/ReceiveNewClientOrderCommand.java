package cmahy.webapp.taco.shop.kernel.application.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.taco.shop.kernel.application.service.ReceiveAndCreateClientOrder;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientNotFoundException;
import cmahy.webapp.taco.shop.kernel.vo.input.ClientOrderInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.ClientOrderOutputVo;
import cmahy.webapp.user.kernel.domain.id.UserId;
import cmahy.webapp.user.kernel.exception.UserNotFoundException;
import jakarta.inject.Named;

@Command
@Named
public class ReceiveNewClientOrderCommand {

    private final ReceiveAndCreateClientOrder receiveAndCreateClientOrder;

    public ReceiveNewClientOrderCommand(ReceiveAndCreateClientOrder receiveAndCreateClientOrder) {
        this.receiveAndCreateClientOrder = receiveAndCreateClientOrder;
    }

    public ClientOrderOutputVo execute(ClientOrderInputVo input, UserId clientId) throws UserNotFoundException, IngredientNotFoundException, RequiredException {
        return receiveAndCreateClientOrder.execute(input, clientId);
    }
}
