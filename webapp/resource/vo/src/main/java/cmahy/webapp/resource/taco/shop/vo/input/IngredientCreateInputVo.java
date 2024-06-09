package cmahy.webapp.resource.taco.shop.vo.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record IngredientCreateInputVo(
    @NotNull
    @NotBlank
    String name,
    @NotNull
    @NotBlank
    String type
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("name", name())
            .append("type", type())
            .toString();
    }
}
