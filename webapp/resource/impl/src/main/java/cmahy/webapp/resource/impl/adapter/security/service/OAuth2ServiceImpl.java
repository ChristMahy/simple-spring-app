package cmahy.webapp.resource.impl.adapter.security.service;

import cmahy.webapp.resource.impl.adapter.security.mapper.output.UserSecurityDetailsMapper;
import cmahy.webapp.resource.impl.adapter.security.service.factory.OAuth2UserInfoFactory;
import cmahy.webapp.resource.impl.adapter.security.vo.google.output.OAuth2UserInfo;
import cmahy.webapp.resource.impl.application.user.command.RegisterUserSecurityCommand;
import cmahy.webapp.resource.impl.application.user.query.GetUserSecurityByUsernameQuery;
import cmahy.webapp.resource.impl.application.user.vo.input.UserSecurityInputAppVo;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.exception.user.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuth2ServiceImpl extends DefaultOAuth2UserService {

    private static final Logger LOG = LoggerFactory.getLogger(OAuth2ServiceImpl.class);

    private final OAuth2UserInfoFactory oAuth2UserInfoFactory;
    private final GetUserSecurityByUsernameQuery getUserSecurityByUsernameQuery;
    private final RegisterUserSecurityCommand registerUserSecurityCommand;
    private final UserSecurityDetailsMapper userSecurityDetailsMapper;

    public OAuth2ServiceImpl(
        OAuth2UserInfoFactory oAuth2UserInfoFactory,
        GetUserSecurityByUsernameQuery getUserSecurityByUsernameQuery,
        RegisterUserSecurityCommand registerUserSecurityCommand,
        UserSecurityDetailsMapper userSecurityDetailsMapper
    ) {
        this.oAuth2UserInfoFactory = oAuth2UserInfoFactory;
        this.getUserSecurityByUsernameQuery = getUserSecurityByUsernameQuery;
        this.registerUserSecurityCommand = registerUserSecurityCommand;
        this.userSecurityDetailsMapper = userSecurityDetailsMapper;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Optional<OAuth2UserInfo> oAuth2UserInfo = oAuth2UserInfoFactory.create(userRequest);

        return getOrCreateUserSecurity(
            oAuth2UserInfo.orElseThrow(() -> new UserNotFoundException(OAuth2UserInfo.class.getSimpleName())),
            AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase())
        );
    }

    private OAuth2User getOrCreateUserSecurity(OAuth2UserInfo oAuth2User, AuthProvider authProvider) throws AuthenticationException {
        UserSecurityOutputAppVo user;

        try {
            user = getUserSecurityByUsernameQuery.execute(
                oAuth2User.email()
                    .map(mail -> StringUtils.isBlank(mail) ? null : mail)
                    .orElseThrow(() -> new UserValidationException(
                        oAuth2User.getClass().getSimpleName(), "mail", "has not to be blanc"
                    )),
                authProvider
            );
        } catch (UserNotFoundException e) {
            user = generateNewUserSecurity(oAuth2User, authProvider);
        }

        return userSecurityDetailsMapper.map(user);
    }

    private UserSecurityOutputAppVo generateNewUserSecurity(OAuth2UserInfo oAuth2User, AuthProvider authProvider) {
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
                authProvider,
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
