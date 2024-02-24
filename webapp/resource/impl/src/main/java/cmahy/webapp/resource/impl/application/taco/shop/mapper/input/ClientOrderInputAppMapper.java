package cmahy.webapp.resource.impl.application.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class ClientOrderInputAppMapper {

    public ClientOrder map(ClientOrderInputAppVo input) {
        if (Objects.isNull(input)) {
            return null;
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

        return clientOrder;
    }
}
