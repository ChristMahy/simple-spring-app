package cmahy.simple.spring.webapp.authorization.application.vo.output;

import cmahy.simple.spring.webapp.authorization.domain.id.UserId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public record UserOutputAppVo(
    UserId id,
    String username,
    byte[] password,
    Set<RoleOutputAppVo> roles
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id())
            .append("username", username())
            .toString();
    }
}
