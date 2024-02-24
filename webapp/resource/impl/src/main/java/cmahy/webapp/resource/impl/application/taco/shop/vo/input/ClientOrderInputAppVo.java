package cmahy.webapp.resource.impl.application.taco.shop.vo.input;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collections;
import java.util.List;

public record ClientOrderInputAppVo(
    String deliveryName,
    String deliveryStreet,
    String deliveryCity,
    String deliveryState,
    String deliveryZip,
    String ccNumber,
    String ccExpiration,
    String ccCVV,
    List<TacoInputAppVo> tacos
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("deliveryName", deliveryName())
            .append("deliveryStreet", deliveryStreet())
            .append("deliveryCity", deliveryCity())
            .append("deliveryState", deliveryState())
            .append("deliveryZip", deliveryZip())
            .append("ccNumber", ccNumber())
            .append("ccExpiration", ccExpiration())
            .append("ccCVV", ccCVV())
            .append("tacos", tacos() == null ? Collections.emptyList() : tacos().stream().map(TacoInputAppVo::name).toList())
            .build();
    }
}
