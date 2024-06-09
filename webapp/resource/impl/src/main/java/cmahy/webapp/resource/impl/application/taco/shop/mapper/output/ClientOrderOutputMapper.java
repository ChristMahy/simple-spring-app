package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.taco.shop.id.ClientOrderId;
import cmahy.webapp.resource.taco.shop.vo.output.ClientOrderOutputVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class ClientOrderOutputMapper {

    private final TacoOutputMapper tacoOutputMapper;

    public ClientOrderOutputMapper(TacoOutputMapper tacoOutputMapper) {
        this.tacoOutputMapper = tacoOutputMapper;
    }

    public ClientOrderOutputVo map(ClientOrder clientOrder) {
        if (Objects.isNull(clientOrder)) {
            throw new NullException(ClientOrder.class);
        }

        return new ClientOrderOutputVo(
            new ClientOrderId(clientOrder.getId()),
            clientOrder.getPlacedAt(),
            clientOrder.getDeliveryName(),
            clientOrder.getDeliveryStreet(),
            clientOrder.getDeliveryCity(),
            clientOrder.getDeliveryState(),
            clientOrder.getDeliveryZip(),
            clientOrder.getCcNumber(),
            clientOrder.getCcExpiration(),
            clientOrder.getCcCVV(),
            clientOrder.getTacos().stream().map(tacoOutputMapper::map).toList()
        );
    }
}
