package cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.help;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record GeneralPropertyVo(
    String message
) {
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("message", message)
            .toString();
    }
}
