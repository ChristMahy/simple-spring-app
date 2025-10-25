package cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.rsocket;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.net.URI;
import java.util.Optional;

public record RSocketPropertyVo(
    String host,
    Integer port,
    URI uri,
    Randomizer randomizer
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("host", host())
            .append("port", port())
            .append("uri", uri())
            .append("randomizer", randomizer())
            .toString();
    }

}
