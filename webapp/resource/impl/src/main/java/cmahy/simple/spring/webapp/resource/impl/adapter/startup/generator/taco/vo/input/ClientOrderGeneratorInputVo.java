package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco.vo.input;

import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.UserSecurityGeneratorInputVo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public record ClientOrderGeneratorInputVo(
    String deliveryName,
    String deliveryStreet,
    String deliveryState,
    String deliveryCity,
    String deliveryZip,
    String ccNumber,
    String ccExpiration,
    String ccCVV,
    List<TacoGeneratorInputVo> tacos,
    UserSecurityGeneratorInputVo user
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("deliveryName", deliveryName())
            .append("deliveryStreet", deliveryStreet())
            .append("deliveryState", deliveryState())
            .append("deliveryCity", deliveryCity())
            .append("deliveryZip", deliveryZip())
            .append("ccNumber", ccNumber())
            .append("ccExpiration", ccExpiration())
            .append("ccCVV", ccCVV())
            .append("tacos", tacos())
            .append("user", user())
            .toString();
    }

}
