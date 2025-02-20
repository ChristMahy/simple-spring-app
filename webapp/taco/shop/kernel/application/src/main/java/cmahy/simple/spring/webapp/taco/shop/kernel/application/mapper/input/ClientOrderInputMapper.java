package cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.input;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.ClientOrderInputVo;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class ClientOrderInputMapper {

    private final ClientOrderBuilderFactory<ClientOrder> clientOrderBuilderFactory;

    public ClientOrderInputMapper(ClientOrderBuilderFactory clientOrderBuilderFactory) {
        this.clientOrderBuilderFactory = clientOrderBuilderFactory;
    }

    public ClientOrder map(ClientOrderInputVo input, User user) throws RequiredException {
        if (Objects.isNull(input)) {
            throw new RequiredException(ClientOrderInputVo.class);
        }

        return clientOrderBuilderFactory.create()
            .deliveryName(input.deliveryName())
            .deliveryZip(input.deliveryZip())
            .deliveryStreet(input.deliveryStreet())
            .deliveryState(input.deliveryState())
            .deliveryCity(input.deliveryCity())
            .ccNumber(input.ccNumber())
            .ccExpiration(input.ccExpiration())
            .ccCVV(input.ccCVV())
            .user(user)
            .build();
    }
}
