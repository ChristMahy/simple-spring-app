package cmahy.webapp.resource.user.vo.output;

import cmahy.webapp.resource.user.id.RoleApiId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record RoleOutputApiVo(
    RoleApiId id,
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
