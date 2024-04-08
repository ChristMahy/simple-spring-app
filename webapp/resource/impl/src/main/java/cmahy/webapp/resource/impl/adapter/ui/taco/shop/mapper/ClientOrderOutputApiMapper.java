package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.ClientOrderApiId;
import cmahy.webapp.resource.ui.taco.vo.output.ClientOrderOutputApiVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClientOrderOutputApiMapper {

    private final TacoOutputApiMapper tacoOutputApiMapper;

    public ClientOrderOutputApiMapper(TacoOutputApiMapper tacoOutputApiMapper) {
        this.tacoOutputApiMapper = tacoOutputApiMapper;
    }

    public ClientOrderOutputApiVo map(ClientOrderOutputAppVo source) {
        if (Objects.isNull(source)) {
            throw new NullException(ClientOrderOutputAppVo.class);
        }

        return new ClientOrderOutputApiVo(
            Objects.nonNull(source.id()) && Objects.nonNull(source.id().value()) ? new ClientOrderApiId(source.id().value()) : null,
            source.placedAt(),
            source.deliveryName(),
            source.deliveryStreet(),
            source.deliveryCity(),
            source.deliveryState(),
            source.deliveryZip(),
            source.ccNumber(),
            source.ccExpiration(),
            source.ccCVV(),
            source.tacos().stream().map(tacoOutputApiMapper::map).toList()
        );
    }
}
