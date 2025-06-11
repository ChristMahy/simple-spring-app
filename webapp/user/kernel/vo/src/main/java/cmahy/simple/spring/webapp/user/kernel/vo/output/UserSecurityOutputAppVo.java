package cmahy.simple.spring.webapp.user.kernel.vo.output;

import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public record UserSecurityOutputAppVo(
    UserId id,
    String userName,
    byte[] password,
    String fullName,
    String street,
    String city,
    String state,
    String zip,
    String phoneNumber,
    AuthProvider authProvider,
    Boolean isExpired,
    Boolean isLocked,
    Boolean isEnabled,
    Boolean isCredentialsExpired,
    Set<RoleOutputAppVo> roles
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id())
            .append("userName", userName())
            .append("fullName", fullName())
            .append("street", street())
            .append("city", city())
            .append("state", state())
            .append("zip", zip())
            .append("phoneNumber", phoneNumber())
            .append("authProvider", authProvider())
            .append("isExpired", isExpired())
            .append("isLocked", isLocked())
            .append("isEnabled", isEnabled())
            .append("isCredentialsExpired", isCredentialsExpired())
            .append("roles", roles())
            .toString();
    }
}
