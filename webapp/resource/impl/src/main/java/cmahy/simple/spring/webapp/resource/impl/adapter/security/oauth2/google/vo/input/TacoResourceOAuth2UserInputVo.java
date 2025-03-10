package cmahy.simple.spring.webapp.resource.impl.adapter.security.oauth2.google.vo.input;

import cmahy.simple.spring.webapp.resource.ui.vo.input.TacoResourceUserSecurityInputVo;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class TacoResourceOAuth2UserInputVo implements OAuth2User, TacoResourceUserSecurityInputVo {

    private final OAuth2User oAuth2User;
    private final UserSecurityOutputAppVo userSecurity;
    private final Collection<? extends GrantedAuthority> authorities;

    public TacoResourceOAuth2UserInputVo(OAuth2User oAuth2User, UserSecurityOutputAppVo userSecurity) {
        this.oAuth2User = oAuth2User;
        this.userSecurity = userSecurity;
        this.authorities = userSecurity.roles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public UserSecurityOutputAppVo userSecurity() {
        return userSecurity;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }
}
