package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public record UserGeneratorInputVo(
    String userName,
    String password,
    String fullName,
    String street,
    String city,
    String state,
    String zip,
    String phoneNumber,
    Set<RoleGeneratorInputVo> roles
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
            .toString();

    }

}
