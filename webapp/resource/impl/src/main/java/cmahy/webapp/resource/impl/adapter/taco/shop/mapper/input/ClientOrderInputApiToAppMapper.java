package cmahy.webapp.resource.impl.adapter.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.input.ClientOrderInputApiVo;
import jakarta.inject.Named;

import java.util.List;
import java.util.Objects;

@Named
public class ClientOrderInputApiToAppMapper {

    public ClientOrderInputAppVo map(ClientOrderInputApiVo input, List<TacoInputAppVo> tacos) {
        if (Objects.isNull(input)) {
            throw new NullException(ClientOrderInputApiVo.class);
        }

        return new ClientOrderInputAppVo(
            input.deliveryName(),
            input.deliveryStreet(),
            input.deliveryCity(),
            input.deliveryState(),
            input.deliveryZip(),
            input.ccNumber(),
            input.ccExpiration(),
            input.ccCVV(),
            tacos
        );
    }
}
