package cmahy.webapp.resource.impl.adapter.security.vo;

import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

public final class UserSecurityDetails implements UserDetails, OAuth2User {

    private final UserSecurityOutputAppVo userSecurity;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    public UserSecurityDetails(UserSecurityOutputAppVo userSecurity) {
        this.userSecurity = userSecurity;

        this.authorities = userSecurity.roles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .toList();

        this.attributes = new HashMap<>();
    }

    public UserSecurityOutputAppVo userSecurity() {
        return userSecurity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return new String(userSecurity.password(), StandardCharsets.UTF_8);
    }

    @Override
    public String getUsername() {
        return userSecurity.userName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.FALSE.equals(userSecurity.isExpired());
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.FALSE.equals(userSecurity.isLocked());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.FALSE.equals(userSecurity.isCredentialsExpired());
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(userSecurity.isEnabled());
    }

    @Override
    public Object getAttribute(String name) {
        return this.getAttributes().get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return Objects.isNull(userSecurity.id()) ? null : userSecurity.id().value().toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("userSecurity", userSecurity)
            .append("authorities", authorities)
            .append("attributes", attributes)
            .toString();
    }
}
