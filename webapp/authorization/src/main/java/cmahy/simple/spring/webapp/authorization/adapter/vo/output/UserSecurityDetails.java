package cmahy.simple.spring.webapp.authorization.adapter.vo.output;

import cmahy.simple.spring.webapp.authorization.application.vo.output.UserOutputAppVo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

public record UserSecurityDetails(
    UserOutputAppVo user
) implements UserDetails {

    @Override
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return user
            .roles().stream()
            .map(role -> new SimpleGrantedAuthority(role.name()))
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
