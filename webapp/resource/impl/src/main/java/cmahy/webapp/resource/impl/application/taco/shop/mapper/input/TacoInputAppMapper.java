package cmahy.webapp.resource.impl.application.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Taco;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class TacoInputAppMapper {

    public Taco map(TacoInputAppVo input) {
        if (Objects.isNull(input)) {
            return null;
        }

        var taco = new Taco();

        taco.setName(input.name());

        return taco;
    }
}
