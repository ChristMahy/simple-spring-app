package cmahy.webapp.resource.impl.application.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import cmahy.webapp.resource.impl.domain.user.User;
import cmahy.webapp.resource.impl.exception.NullException;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class ClientOrderInputAppMapper {

    public ClientOrder map(ClientOrderInputAppVo input, User user) {
        if (Objects.isNull(input)) {
            throw new NullException(ClientOrderInputAppVo.class);
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
