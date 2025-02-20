package cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.input;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.TacoInputVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class TacoInputMapper {

    private final TacoBuilderFactory<Taco> tacoBuilderFactory;

    public TacoInputMapper(TacoBuilderFactory tacoBuilderFactory) {
        this.tacoBuilderFactory = tacoBuilderFactory;
    }

    public Taco map(TacoInputVo input) throws RequiredException {
        if (Objects.isNull(input)) {
            throw new RequiredException(TacoInputVo.class);
        }

        return tacoBuilderFactory.create()
            .name(input.name())
            .build();
    }
}
