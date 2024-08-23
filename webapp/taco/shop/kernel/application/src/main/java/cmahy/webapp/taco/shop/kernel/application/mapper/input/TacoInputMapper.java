package cmahy.webapp.taco.shop.kernel.application.mapper.input;

import cmahy.webapp.taco.shop.kernel.domain.Taco;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.input.TacoInputVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class TacoInputMapper {

    public Taco map(TacoInputVo input) throws RequiredException {
        if (Objects.isNull(input)) {
            throw new RequiredException(TacoInputVo.class);
        }

        var taco = new Taco();

        taco.setName(input.name());

        return taco;
    }
}
