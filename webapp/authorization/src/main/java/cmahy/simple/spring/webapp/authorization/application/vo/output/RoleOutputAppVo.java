package cmahy.simple.spring.webapp.authorization.application.vo.output;

import cmahy.simple.spring.webapp.authorization.domain.id.RoleId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record RoleOutputAppVo(
    RoleId roleId,
    String name
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("roleId", roleId())
            .append("name", name())
            .toString();
    }
}
