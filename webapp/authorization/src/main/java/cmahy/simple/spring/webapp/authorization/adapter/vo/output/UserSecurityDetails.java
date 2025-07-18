package cmahy.simple.spring.webapp.authorization.adapter.vo.output;

import cmahy.simple.spring.webapp.authorization.application.vo.output.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record UserSecurityDetails(
    UserOutputAppVo user
) implements UserDetails {

    @Override
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return Stream.of(
                user.roles().stream().map(RoleOutputAppVo::name),
                user.roles().stream().flatMap(r -> r.rights().stream()).map(RightOutputAppVo::name)
            )
            .flatMap(s -> s)
            .distinct()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return new String(user.password(), StandardCharsets.UTF_8);
    }

    @Override
    public String getUsername() {
        return user.username();
    }
}
