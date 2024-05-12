package cmahy.webapp.resource.ui.taco.vo.output;

import cmahy.webapp.resource.ui.taco.vo.id.ClientOrderUiId;

import java.util.Date;
import java.util.List;

public record ClientOrderOutputUiVo(
    ClientOrderUiId id,
    Date placedAt,
    String deliveryName,
    String deliveryStreet,
    String deliveryCity,
    String deliveryState,
    String deliveryZip,
    String ccNumber,
    String ccExpiration,
    String ccCVV,
    List<TacoOutputUiVo> tacos
) {
}
