package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderPageOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.page.ClientOrderPage;
import jakarta.inject.Named;

@Named
public class ClientOrderPageOutputMapper {

    private final ClientOrderOutputMapper clientOrderOutputMapper;

    public ClientOrderPageOutputMapper(ClientOrderOutputMapper clientOrderOutputMapper) {
        this.clientOrderOutputMapper = clientOrderOutputMapper;
    }

    public ClientOrderPageOutputAppVo map(ClientOrderPage source) {
        return new ClientOrderPageOutputAppVo(
            source.content().stream().map(clientOrderOutputMapper::map).toList(),
            source.totalElements()
        );
    }
}
