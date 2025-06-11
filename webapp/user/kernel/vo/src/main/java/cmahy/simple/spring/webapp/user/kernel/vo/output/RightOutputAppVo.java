package cmahy.simple.spring.webapp.user.kernel.vo.output;

import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record RightOutputAppVo(
    RightId id,
    String name
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id())
            .append("name", name())
            .toString();
    }

}
