package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.info;

import jakarta.validation.Valid;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

import static java.util.Map.entry;

public record ContactProperties(
    String email,
    String phone
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("email", email())
            .append("phone", phone())
            .toString();
    }

    public Map<String, Object> toMap() {
        return Map.ofEntries(entry(
            "contact",
            Map.ofEntries(
                entry("email", email()),
                entry("phone", phone())
            )
        ));
    }
}
