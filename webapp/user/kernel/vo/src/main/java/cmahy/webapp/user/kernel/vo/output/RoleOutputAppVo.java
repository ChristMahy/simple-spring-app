package cmahy.webapp.user.kernel.vo.output;

import cmahy.webapp.user.kernel.domain.id.RoleId;
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
