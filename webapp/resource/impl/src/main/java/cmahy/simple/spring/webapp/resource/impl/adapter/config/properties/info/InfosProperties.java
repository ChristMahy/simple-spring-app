package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.info;

import jakarta.validation.Valid;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

import static java.util.Map.entry;

@ConfigurationProperties(prefix = "infos")
@Validated
public record InfosProperties(
    @Valid ContactProperties contact
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("contact", contact())
            .toString();
    }

    public Map<String, Object> toMap() {
        return Map.ofEntries(entry(
            "infos",
            contact().toMap()
        ));
    }
}
