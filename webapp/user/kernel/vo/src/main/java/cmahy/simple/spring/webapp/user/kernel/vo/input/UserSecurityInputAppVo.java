package cmahy.simple.spring.webapp.user.kernel.vo.input;

import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record UserSecurityInputAppVo(
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
    Boolean isCredentialsExpired
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("userName", userName)
            .append("password", "...")
            .append("fullName", fullName)
            .append("street", street)
            .append("city", city)
            .append("state", state)
            .append("zip", zip)
            .append("phoneNumber", phoneNumber)
            .append("authProvider", authProvider)
            .append("isExpired", isExpired)
            .append("isLocked", isLocked)
            .append("isEnabled", isEnabled)
            .append("isCredentialsExpired", isCredentialsExpired)
            .toString();
    }
}
