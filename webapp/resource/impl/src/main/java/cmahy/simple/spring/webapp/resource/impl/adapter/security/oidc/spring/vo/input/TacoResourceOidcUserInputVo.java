package cmahy.simple.spring.webapp.resource.impl.adapter.security.oidc.spring.vo.input;

import cmahy.simple.spring.webapp.resource.ui.vo.input.TacoResourceUserSecurityInputVo;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TacoResourceOidcUserInputVo implements OidcUser, TacoResourceUserSecurityInputVo {

    private final OidcUser oidcUser;
    private final UserSecurityOutputAppVo userSecurity;
    private final Collection<? extends GrantedAuthority> authorities;

    public TacoResourceOidcUserInputVo(OidcUser oidcUser, UserSecurityOutputAppVo userSecurity) {
        this.oidcUser = oidcUser;
        this.userSecurity = userSecurity;

        this.authorities = Stream.concat(
            oidcUser.getAuthorities().stream(),
            userSecurity.roles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
        )
            .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public UserSecurityOutputAppVo userSecurity() {
        return this.userSecurity;
    }

    @Override
    public Map<String, Object> getClaims() {
        return oidcUser.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return oidcUser.getUserInfo();
    }

    @Override
    public OidcIdToken getIdToken() {
        return oidcUser.getIdToken();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oidcUser.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return oidcUser.getName();
    }
}
