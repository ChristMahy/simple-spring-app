package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.domain.taco.id.ClientOrderId;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import jakarta.inject.Named;

@Named
public class ClientOrderOutputMapper {

    private final TacoOutputMapper tacoOutputMapper;

    public ClientOrderOutputMapper(TacoOutputMapper tacoOutputMapper) {
        this.tacoOutputMapper = tacoOutputMapper;
    }

    public ClientOrderOutputAppVo map(ClientOrder clientOrder) {
        return new ClientOrderOutputAppVo(
            new ClientOrderId(clientOrder.getId()),
            clientOrder.getPlacedAt(),
            clientOrder.getTacos().stream().map(tacoOutputMapper::map).toList()
        );
    }
}
