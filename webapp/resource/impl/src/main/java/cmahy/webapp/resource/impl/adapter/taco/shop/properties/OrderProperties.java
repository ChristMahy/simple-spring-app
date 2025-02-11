package cmahy.webapp.resource.impl.adapter.taco.shop.properties;

import cmahy.webapp.resource.impl.adapter.taco.shop.properties.nested.NestedSampleProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "application.taco.orders")
public record OrderProperties(
    @Min(value = 5, message = "Must be between 5 and 25")
    @Max(value = 25, message = "Must be between 5 and 25")
    int pageSize,
    @Valid
    @NestedConfigurationProperty
    NestedSampleProperties subSamples
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("pageSize", pageSize())
            .append("subSamples", subSamples())
            .toString();
    }
}
