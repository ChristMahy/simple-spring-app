package cmahy.webapp.resource.user.api.vo.output;

import cmahy.webapp.resource.user.api.vo.id.RoleApiId;
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
