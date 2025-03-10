package cmahy.simple.spring.webapp.resource.impl.adapter.security.oidc.spring.service;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.oidc.TacoResourceOidcService;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.oidc.spring.vo.input.TacoResourceOidcUserInputVo;
import cmahy.simple.spring.webapp.user.kernel.application.command.RegisterUserSecurityCommand;
import cmahy.simple.spring.webapp.user.kernel.application.query.GetUserSecurityByUsernameQuery;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.exception.*;
import cmahy.simple.spring.webapp.user.kernel.vo.input.UserSecurityInputAppVo;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TacoResourceSpringOidcServiceImpl extends OidcUserService implements TacoResourceOidcService, OAuth2UserService<OidcUserRequest, OidcUser> {

    private static final Logger LOG = LoggerFactory.getLogger(TacoResourceSpringOidcServiceImpl.class);

    private final RegisterUserSecurityCommand registerUserSecurityCommand;
    private final GetUserSecurityByUsernameQuery getUserSecurityByUsernameQuery;

    public TacoResourceSpringOidcServiceImpl(
        RegisterUserSecurityCommand registerUserSecurityCommand,
        GetUserSecurityByUsernameQuery getUserSecurityByUsernameQuery
    ) {
        this.registerUserSecurityCommand = registerUserSecurityCommand;
        this.getUserSecurityByUsernameQuery = getUserSecurityByUsernameQuery;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        try {
            UserSecurityOutputAppVo userSecurity = getOrCreateUserSecurity(oidcUser);

            return new TacoResourceOidcUserInputVo(super.loadUser(userRequest), userSecurity);
        } catch (ValidationException validationException) {
            throw new OAuth2AuthenticationException(new OAuth2Error("user-validation-failure"), validationException);
        }
    }

    private UserSecurityOutputAppVo getOrCreateUserSecurity(OidcUser oidcUser) throws AuthenticationException, UserValidationException {
        UserSecurityOutputAppVo user;

        try {
            user = getUserSecurityByUsernameQuery.execute(
                Optional.ofNullable(oidcUser.getName())
                    .map(name -> StringUtils.isBlank(name) ? null : name)
                    .orElseThrow(() -> new UserValidationException(
                        oidcUser.getClass().getSimpleName(), "name", "has not to be blanc"
                    )),
                AuthProvider.OIDC_SPRING
            );
        } catch (UserNotFoundException e) {
            user = generateNewUserSecurity(oidcUser);
        }

        return user;
    }

    private UserSecurityOutputAppVo generateNewUserSecurity(OidcUser oidcUser) {
        try {
            return registerUserSecurityCommand.execute(new UserSecurityInputAppVo(
                oidcUser.getName(),
                new byte[0],
                String.join(" ",
                    oidcUser.getName(),
                    oidcUser.getFamilyName()
                ),
                "",
                "",
                "",
                "",
                "",
                AuthProvider.OIDC_SPRING,
                false,
                false,
                true,
                false
            ));
        } catch (UserExistsException userExistsException) {
            throw new OAuth2AuthenticationException(new OAuth2Error("user-exists"), userExistsException);
        } catch (Exception any) {
            LOG.warn(any.getMessage(), any);

            throw new OAuth2AuthenticationException(new OAuth2Error("error-registration"), any);
        }
    }
}
