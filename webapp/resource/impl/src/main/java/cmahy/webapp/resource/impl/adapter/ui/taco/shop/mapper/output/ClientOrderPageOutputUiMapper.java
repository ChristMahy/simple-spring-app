package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderPageOutputAppVo;
import cmahy.webapp.resource.ui.taco.vo.output.ClientOrderPageOutputUiVo;
import jakarta.inject.Named;

@Named
public class ClientOrderPageOutputUiMapper {

    private final ClientOrderOutputUiMapper clientOrderOutputUiMapper;

    public ClientOrderPageOutputUiMapper(ClientOrderOutputUiMapper clientOrderOutputUiMapper) {
        this.clientOrderOutputUiMapper = clientOrderOutputUiMapper;
    }

    public ClientOrderPageOutputUiVo map(ClientOrderPageOutputAppVo source) {
        return new ClientOrderPageOutputUiVo(
            source.content().stream().map(clientOrderOutputUiMapper::map).toList(),
            source.totalElements()
        );
    }
}
