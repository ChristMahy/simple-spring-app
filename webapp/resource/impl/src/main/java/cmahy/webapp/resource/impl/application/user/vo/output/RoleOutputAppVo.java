package cmahy.webapp.resource.impl.application.user.vo.output;

import cmahy.webapp.resource.impl.domain.user.id.RoleId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record RoleOutputAppVo(
    RoleId id,
    String name
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id)
            .append("name", name)
            .toString();
    }
}
