package cmahy.webapp.resource.impl.application.taco.shop.vo.output;

import cmahy.webapp.resource.impl.domain.taco.id.ClientOrderId;

import java.util.Date;
import java.util.List;

public record ClientOrderOutputAppVo(
    ClientOrderId id,
    Date placedAt,
    List<TacoOutputAppVo> tacos
) {
}
