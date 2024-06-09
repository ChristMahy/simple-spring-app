package cmahy.webapp.resource.impl.application.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.domain.taco.Taco;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.taco.shop.vo.input.TacoInputVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class TacoInputMapper {

    public Taco map(TacoInputVo input) {
        if (Objects.isNull(input)) {
            throw new NullException(TacoInputVo.class);
        }

        var taco = new Taco();

        taco.setName(input.name());

        return taco;
    }
}
