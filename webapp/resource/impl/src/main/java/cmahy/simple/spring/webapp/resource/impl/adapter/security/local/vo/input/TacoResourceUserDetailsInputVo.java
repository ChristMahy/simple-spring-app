package cmahy.simple.spring.webapp.resource.impl.adapter.security.local.vo.input;

import cmahy.simple.spring.webapp.resource.ui.vo.input.TacoResourceUserSecurityInputVo;
import cmahy.simple.spring.webapp.user.kernel.vo.output.RoleOutputAppVo;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TacoResourceUserDetailsInputVo implements UserDetails, TacoResourceUserSecurityInputVo {

    private final UserSecurityOutputAppVo userSecurity;
    private final Collection<? extends GrantedAuthority> authorities;

    public TacoResourceUserDetailsInputVo(
        UserSecurityOutputAppVo userSecurity
    ) {
        this.userSecurity = userSecurity;

        Set<RoleOutputAppVo> roles = userSecurity.roles();

        this.authorities = Stream.concat(
                roles.stream()
                    .map(role -> "ROLE_" + role.name()),
                roles.stream()
                    .flatMap(role -> role.rights().stream())
                    .map(right -> "SCOPE_" + right.name())
            )
            .distinct()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public UserSecurityOutputAppVo userSecurity() {
        return this.userSecurity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getName() {
        return userSecurity.id().value().toString();
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
        return !userSecurity.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userSecurity.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !userSecurity.isCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return userSecurity.isEnabled();
    }
}
