package cmahy.webapp.resource.impl.adapter.taco.shop.properties;

import cmahy.webapp.resource.impl.adapter.taco.shop.properties.nested.NestedPropertiesSample;
import cmahy.webapp.resource.impl.application.taco.shop.properties.OrderProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "taco.orders")
@Validated
public record OrderPropertiesImpl(
    @Min(value = 5, message = "Must be between 5 and 25")
    @Max(value = 25, message = "Must be between 5 and 25")
    int pageSize,
    @Valid
    NestedPropertiesSample subSamples
) implements OrderProperties {

    public OrderPropertiesImpl() {
        this(20, new NestedPropertiesSample("exact-match"));
    }
}
