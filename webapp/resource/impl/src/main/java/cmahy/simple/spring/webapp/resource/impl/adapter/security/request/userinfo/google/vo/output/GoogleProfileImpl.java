package cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.google.vo.output;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.vo.output.OAuth2Profile;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record GoogleProfileImpl(
    String sub,
    String name,
    String email,
    String given_name,
    String family_name
) implements OAuth2Profile {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("sub", sub)
            .append("name", name)
            .append("email", email)
            .append("given_name", given_name)
            .append("family_name", family_name)
            .toString();
    }
}
