package cmahy.simple.spring.webapp.authorization.application.vo.output;

import cmahy.simple.spring.webapp.authorization.domain.id.RoleId;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public record RoleOutputAppVo(
    RoleId roleId,
    String name,
    Set<RightOutputAppVo> rights
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("roleId", roleId())
            .append("name", name())
            .append("rights", StringUtils.join(rights(), ", "))
            .toString();
    }
}
