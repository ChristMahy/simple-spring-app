package cmahy.webapp.shell.client.impl.application.vo.property.help;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record HelpPropertyVo(
    GeneralPropertyVo general
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("general", general)
            .toString();
    }
}
