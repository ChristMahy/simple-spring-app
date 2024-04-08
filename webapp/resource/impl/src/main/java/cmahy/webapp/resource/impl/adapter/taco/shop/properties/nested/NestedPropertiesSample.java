package cmahy.webapp.resource.impl.adapter.taco.shop.properties.nested;

import jakarta.validation.constraints.NotBlank;

public record NestedPropertiesSample(
    @NotBlank
    @SampleConstraint
    String testIt
) {
}
