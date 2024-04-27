package cmahy.webapp.resource.impl.adapter.security.vo.google.input;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record RegisterUser(
    String email,
    String password,
    String repassword,
    String firstName,
    String lastName
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("email", email)
            .append("firstName", firstName)
            .append("lastName", lastName)
            .toString();
    }
}
