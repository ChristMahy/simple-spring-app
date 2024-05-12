package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.input.ClientOrderInputUiVo;
import jakarta.inject.Named;

import java.util.List;
import java.util.Objects;

@Named
public class ClientOrderInputUiToAppMapper {

    public ClientOrderInputAppVo map(ClientOrderInputUiVo input, List<TacoInputAppVo> tacos) {
        if (Objects.isNull(input)) {
            throw new NullException(ClientOrderInputUiVo.class);
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
