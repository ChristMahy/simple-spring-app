package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input;

import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public record UserSecurityGeneratorInputVo(
    String userName,
    String password,
    String fullName,
    String street,
    String city,
    String state,
    String zip,
    String phoneNumber,
    Set<RoleGeneratorInputVo> roles,
    AuthProvider authProvider,
    Boolean expired,
    Boolean locked,
    Boolean enabled,
    Boolean credentialsExpired
) {

    @Override
    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("userName", userName())
            .append("password", password())
            .append("fullName", fullName())
            .append("street", street())
            .append("city", city())
            .append("state", state())
            .append("zip", zip())
            .append("phoneNumber", phoneNumber())
            .append("roles", roles())
            .append("authProvider", authProvider())
            .append("expired", expired())
            .append("locked", locked())
            .append("enabled", enabled())
            .append("credentialsExpired", credentialsExpired())
            .toString();

    }

}
