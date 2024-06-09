package cmahy.webapp.resource.impl.application.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import cmahy.webapp.resource.impl.domain.user.User;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.taco.shop.vo.input.ClientOrderInputVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class ClientOrderInputMapper {

    public ClientOrder map(ClientOrderInputVo input, User user) {
        if (Objects.isNull(input)) {
            throw new NullException(ClientOrderInputVo.class);
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
