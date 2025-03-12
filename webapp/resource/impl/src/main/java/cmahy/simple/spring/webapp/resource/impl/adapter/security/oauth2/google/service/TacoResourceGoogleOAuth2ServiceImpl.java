package cmahy.simple.spring.webapp.resource.impl.adapter.security.oauth2.google.service;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.oauth2.TacoResourceOAuth2Service;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.oauth2.google.vo.input.TacoResourceOAuth2UserInputVo;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.oauth2.google.vo.output.GoogleOAuth2User;
import cmahy.simple.spring.webapp.user.kernel.application.command.RegisterUserSecurityCommand;
import cmahy.simple.spring.webapp.user.kernel.application.query.GetUserSecurityByUsernameQuery;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.exception.*;
import cmahy.simple.spring.webapp.user.kernel.vo.input.UserSecurityInputAppVo;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "spring-app.security.oauth2.enable", havingValue = "true")
public class TacoResourceGoogleOAuth2ServiceImpl extends DefaultOAuth2UserService implements TacoResourceOAuth2Service, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final Logger LOG = LoggerFactory.getLogger(TacoResourceGoogleOAuth2ServiceImpl.class);

    private final GetUserSecurityByUsernameQuery getUserSecurityByUsernameQuery;
    private final RegisterUserSecurityCommand registerUserSecurityCommand;

    public TacoResourceGoogleOAuth2ServiceImpl(
        GetUserSecurityByUsernameQuery getUserSecurityByUsernameQuery,
        RegisterUserSecurityCommand registerUserSecurityCommand
    ) {
        this.getUserSecurityByUsernameQuery = getUserSecurityByUsernameQuery;
        this.registerUserSecurityCommand = registerUserSecurityCommand;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        GoogleOAuth2User oAuth2User = new GoogleOAuth2User(super.loadUser(userRequest));

        try {
            UserSecurityOutputAppVo userSecurity = getOrCreateUserSecurity(oAuth2User);

            return new TacoResourceOAuth2UserInputVo(oAuth2User.oAuth2User(), userSecurity);
        } catch (ValidationException validationException) {
            throw new OAuth2AuthenticationException(new OAuth2Error("user-validation-failure"), validationException);
        }
    }

    private UserSecurityOutputAppVo getOrCreateUserSecurity(GoogleOAuth2User oAuth2User) throws AuthenticationException, UserValidationException {
        UserSecurityOutputAppVo user;

        try {
            user = getUserSecurityByUsernameQuery.execute(
                oAuth2User.email()
                    .map(mail -> StringUtils.isBlank(mail) ? null : mail)
                    .orElseThrow(() -> new UserValidationException(
                        oAuth2User.getClass().getSimpleName(), "mail", "has not to be blanc"
                    )),
                AuthProvider.GOOGLE
            );
        } catch (UserNotFoundException e) {
            user = generateNewUserSecurity(oAuth2User);
        }

        return user;
    }

    private UserSecurityOutputAppVo generateNewUserSecurity(GoogleOAuth2User oAuth2User) {
        try {
            return registerUserSecurityCommand.execute(new UserSecurityInputAppVo(
                oAuth2User.email().orElseThrow(() -> new UserValidationException(oAuth2User.getClass().getSimpleName(), "mail", "has not to be blanc")),
                new byte[0],
                String.join(" ",
                    oAuth2User.firstName().orElse(""),
                    oAuth2User.lastName().orElse("")
                ),
                "",
                "",
                "",
                "",
                "",
                AuthProvider.GOOGLE,
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
