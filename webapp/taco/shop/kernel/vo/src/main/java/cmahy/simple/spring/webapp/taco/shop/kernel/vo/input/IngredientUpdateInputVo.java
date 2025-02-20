package cmahy.simple.spring.webapp.taco.shop.kernel.vo.input;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

public record IngredientUpdateInputVo(
    Optional<String> name,
    Optional<String> type
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("name", name())
            .append("type", type())
            .toString();
    }
}
