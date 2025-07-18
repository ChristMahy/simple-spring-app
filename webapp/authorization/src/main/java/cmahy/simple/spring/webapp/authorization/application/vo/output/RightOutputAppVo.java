package cmahy.simple.spring.webapp.authorization.application.vo.output;

import cmahy.simple.spring.webapp.authorization.domain.id.RightId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record RightOutputAppVo(
    RightId rightId,
    String name
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("rightId", rightId())
            .append("name", name())
            .toString();
    }
}
