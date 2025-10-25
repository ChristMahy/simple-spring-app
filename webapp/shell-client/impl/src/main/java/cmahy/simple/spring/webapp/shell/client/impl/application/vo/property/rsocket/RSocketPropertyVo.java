package cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.rsocket;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record RSocketPropertyVo(
    String host,
    Integer port,
    Randomizer randomizer
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("host", host())
            .append("port", port())
            .append("randomizer", randomizer())
            .toString();
    }

}
