package cmahy.webapp.taco.shop.kernel.application.mapper.input;

import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.input.ClientOrderInputVo;
import cmahy.webapp.user.kernel.domain.User;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class ClientOrderInputMapper {

    public ClientOrder map(ClientOrderInputVo input, User user) throws RequiredException {
        if (Objects.isNull(input)) {
            throw new RequiredException(ClientOrderInputVo.class);
        }

        var clientOrder = new ClientOrder();

        clientOrder.setDeliveryName(input.deliveryName());
        clientOrder.setDeliveryZip(input.deliveryZip());
        clientOrder.setDeliveryStreet(input.deliveryStreet());
        clientOrder.setDeliveryState(input.deliveryState());
        clientOrder.setDeliveryCity(input.deliveryCity());
        clientOrder.setCcNumber(input.ccNumber());
        clientOrder.setCcExpiration(input.ccExpiration());
        clientOrder.setCcCVV(input.ccCVV());

        clientOrder.setUser(user);

        return clientOrder;
    }
}
