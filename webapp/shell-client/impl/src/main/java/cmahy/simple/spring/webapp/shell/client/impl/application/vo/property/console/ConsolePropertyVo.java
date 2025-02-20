package cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.console;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record ConsolePropertyVo(
    OutputPropertyVo output
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("output", output)
            .toString();
    }
}
