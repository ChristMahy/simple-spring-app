package cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.ClientOrderId;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.ClientOrderOutputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.TacoOutputVo;
import jakarta.inject.Named;

import java.util.*;

@Named
public class ClientOrderOutputMapper {

    private final TacoOutputMapper tacoOutputMapper;

    public ClientOrderOutputMapper(TacoOutputMapper tacoOutputMapper) {
        this.tacoOutputMapper = tacoOutputMapper;
    }

    public ClientOrderOutputVo map(ClientOrder clientOrder) throws RequiredException {
        if (Objects.isNull(clientOrder)) {
            throw new RequiredException(ClientOrder.class);
        }

        ArrayList<TacoOutputVo> tacosVo = new ArrayList<>(clientOrder.getTacos().size());

        for (Taco taco : clientOrder.getTacos()) {
            tacosVo.add(tacoOutputMapper.map(taco));
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
            Collections.unmodifiableList(tacosVo)
        );
    }
}
