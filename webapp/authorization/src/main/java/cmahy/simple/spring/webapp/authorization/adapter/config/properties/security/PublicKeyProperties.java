package cmahy.simple.spring.webapp.authorization.adapter.config.properties.security;

import cmahy.simple.spring.security.common.api.rsa.vo.id.PublicKeyId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;
import java.util.stream.Collectors;

public record PublicKeyProperties(
    @Valid
    Map<PublicKeyId, @NotBlank String> locations
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append(
                "locations",
                Optional.ofNullable(locations())
                    .map(Map::keySet)
                    .orElse(Collections.emptySet())
                    .stream()
                    .map(PublicKeyId::toString)
                    .collect(Collectors.joining(", "))
            )
            .toString();
    }

}
