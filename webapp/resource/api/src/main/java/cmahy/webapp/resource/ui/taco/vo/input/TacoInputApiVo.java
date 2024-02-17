package cmahy.webapp.resource.ui.taco.vo.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public record TacoInputApiVo(
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    String name,
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    List<String> ingredientIds
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("name", name)
            .append("ingredientIds", ingredientIds)
            .build();
    }
}
