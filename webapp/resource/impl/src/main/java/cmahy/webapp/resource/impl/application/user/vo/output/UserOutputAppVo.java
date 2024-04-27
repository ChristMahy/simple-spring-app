package cmahy.webapp.resource.impl.application.user.vo.output;

import cmahy.webapp.resource.impl.domain.user.id.UserId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record UserOutputAppVo(
    UserId id,
    String userName,
    byte[] password,
    String fullName,
    String street,
    String city,
    String state,
    String zip,
    String phoneNumber
) {
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id)
            .append("userName", userName)
            .append("fullName", fullName)
            .append("street", street)
            .append("city", city)
            .append("state", state)
            .append("zip", zip)
            .append("phoneNumber", phoneNumber)
            .toString();
    }
}
