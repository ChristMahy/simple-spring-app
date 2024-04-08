package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderPageOutputAppVo;
import cmahy.webapp.resource.ui.taco.vo.output.ClientOrderPageOutputApiVo;
import jakarta.inject.Named;

@Named
public class ClientOrderPageOutputApiMapper {

    private final ClientOrderOutputApiMapper clientOrderOutputApiMapper;

    public ClientOrderPageOutputApiMapper(ClientOrderOutputApiMapper clientOrderOutputApiMapper) {
        this.clientOrderOutputApiMapper = clientOrderOutputApiMapper;
    }

    public ClientOrderPageOutputApiVo map(ClientOrderPageOutputAppVo source) {
        return new ClientOrderPageOutputApiVo(
            source.content().stream().map(clientOrderOutputApiMapper::map).toList(),
            source.totalElements()
        );
    }
}
