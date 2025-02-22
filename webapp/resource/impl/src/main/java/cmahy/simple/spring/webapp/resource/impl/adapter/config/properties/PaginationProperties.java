package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties;

import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "application.pagination")
public record PaginationProperties(
    @NotNull
    @Min(value = 5, message = "Must be between 5 and 25")
    @Max(value = 25, message = "Must be between 5 and 25")
    Integer pageSize
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("pageSize", pageSize())
            .toString();
    }
}
