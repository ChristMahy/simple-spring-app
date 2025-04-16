package cmahy.simple.spring.security.webclient.api.vo.output;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public record BasicCredentialOutputVo(
    Optional<byte[]> username,
    Optional<byte[]> password
) {

    public Optional<String> usernameAsString() {
        return username().map(u -> new String(u, StandardCharsets.UTF_8));
    }

    public Optional<String> passwordAsString() {
        return password().map(p -> new String(p, StandardCharsets.UTF_8));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("username", usernameAsString().orElse(null))
            .append("password", "*******")
            .toString();
    }
}
