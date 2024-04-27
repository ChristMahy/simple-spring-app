package cmahy.webapp.resource.impl.adapter.taco.shop.properties.nested;

import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record NestedPropertiesSample(
    @NotBlank
    @SampleConstraint
    String testIt
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("testIt", testIt())
            .toString();
    }
}
