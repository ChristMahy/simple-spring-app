package cmahy.webapp.taco.shop.kernel.vo.output;

import cmahy.webapp.taco.shop.kernel.domain.id.ClientOrderId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

public record ClientOrderOutputVo(
    ClientOrderId id,
    Date placedAt,
    String deliveryName,
    String deliveryStreet,
    String deliveryCity,
    String deliveryState,
    String deliveryZip,
    String ccNumber,
    String ccExpiration,
    String ccCVV,
    List<TacoOutputVo> tacos
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id())
            .append("placedAt", placedAt())
            .append("deliveryName", deliveryName())
            .append("deliveryStreet", deliveryStreet())
            .append("deliveryCity", deliveryCity())
            .append("deliveryState", deliveryState())
            .append("deliveryZip", deliveryZip())
            .append("ccNumber", ccNumber())
            .append("ccExpiration", ccExpiration())
            .append("ccCVV", ccCVV())
            .append("tacos", tacos())
            .toString();
    }
}
