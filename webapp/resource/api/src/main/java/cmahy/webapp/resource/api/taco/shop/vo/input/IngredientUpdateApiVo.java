package cmahy.webapp.resource.api.taco.shop.vo.input;

import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

public record IngredientUpdateApiVo(
    @NotBlank
    Optional<String> name,
    @NotBlank
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
