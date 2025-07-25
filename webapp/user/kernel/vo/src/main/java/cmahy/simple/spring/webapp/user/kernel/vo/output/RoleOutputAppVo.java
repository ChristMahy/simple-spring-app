package cmahy.simple.spring.webapp.user.kernel.vo.output;

import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public record RoleOutputAppVo(
    RoleId id,
    String name,
    Set<RightOutputAppVo> rights
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id())
            .append("name", name())
            .append("rights", rights())
            .toString();
    }
}
