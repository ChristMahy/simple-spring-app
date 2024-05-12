package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.ClientOrderUiId;
import cmahy.webapp.resource.ui.taco.vo.output.ClientOrderOutputUiVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClientOrderOutputUiMapper {

    private final TacoOutputUiMapper tacoOutputUiMapper;

    public ClientOrderOutputUiMapper(TacoOutputUiMapper tacoOutputUiMapper) {
        this.tacoOutputUiMapper = tacoOutputUiMapper;
    }

    public ClientOrderOutputUiVo map(ClientOrderOutputAppVo source) {
        if (Objects.isNull(source)) {
            throw new NullException(ClientOrderOutputAppVo.class);
        }

        return new ClientOrderOutputUiVo(
            Objects.nonNull(source.id()) && Objects.nonNull(source.id().value()) ? new ClientOrderUiId(source.id().value()) : null,
            source.placedAt(),
            source.deliveryName(),
            source.deliveryStreet(),
            source.deliveryCity(),
            source.deliveryState(),
            source.deliveryZip(),
            source.ccNumber(),
            source.ccExpiration(),
            source.ccCVV(),
            source.tacos().stream().map(tacoOutputUiMapper::map).toList()
        );
    }
}
