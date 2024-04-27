package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderPageOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.page.ClientOrderPage;
import cmahy.webapp.resource.impl.exception.NullException;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class ClientOrderPageOutputMapper {

    private final ClientOrderOutputMapper clientOrderOutputMapper;

    public ClientOrderPageOutputMapper(ClientOrderOutputMapper clientOrderOutputMapper) {
        this.clientOrderOutputMapper = clientOrderOutputMapper;
    }

    public ClientOrderPageOutputAppVo map(ClientOrderPage source) {
        if (Objects.isNull(source)) {
            throw new NullException(ClientOrderPage.class);
        }

        return new ClientOrderPageOutputAppVo(
            source.content().stream().map(clientOrderOutputMapper::map).toList(),
            source.totalElements()
        );
    }
}
