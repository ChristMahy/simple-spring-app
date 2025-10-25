package cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.rsocket;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record Randomizer(
    Integer stringLength
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("stringLength", stringLength())
            .toString();
    }

}
