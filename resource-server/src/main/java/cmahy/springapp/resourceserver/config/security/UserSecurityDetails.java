package cmahy.springapp.resourceserver.config.security;

import cmahy.springapp.resourceserver.domain.User;
import cmahy.springapp.resourceserver.security.oauth2.model.OAuth2UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class UserSecurityDetails implements UserDetails, OAuth2User {
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    public UserSecurityDetails(User user) {
        this.user = user;
        this.authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toList());
        this.attributes = new HashMap<>();
    }

    public UserSecurityDetails(User user, OAuth2UserInfo oAuth2UserInfo) {
        this.user = user;
        this.authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toList());
        this.attributes = oAuth2UserInfo.getAttributes();
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsExpired() == Boolean.FALSE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsLocked() == Boolean.FALSE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsCredentialsExpired() == Boolean.FALSE;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnabled() == Boolean.TRUE;
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
        return String.valueOf(user.getId());
    }
}
