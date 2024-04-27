package cmahy.webapp.resource.impl.adapter.security.service;

import cmahy.webapp.resource.impl.adapter.security.mapper.output.UserSecurityDetailsMapper;
import cmahy.webapp.resource.impl.adapter.security.service.factory.OAuth2UserInfoFactory;
import cmahy.webapp.resource.impl.adapter.security.vo.UserSecurityDetails;
import cmahy.webapp.resource.impl.adapter.security.vo.google.output.OAuth2UserInfo;
import cmahy.webapp.resource.impl.application.user.command.RegisterUserSecurityCommand;
import cmahy.webapp.resource.impl.application.user.query.GetUserSecurityByUsernameQuery;
import cmahy.webapp.resource.impl.application.user.vo.input.UserSecurityInputAppVo;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.exception.user.UserNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuth2ServiceImpl extends DefaultOAuth2UserService {

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

        return process(oAuth2UserInfo.orElseThrow(() -> new UserNotFoundException(OAuth2UserInfo.class.getSimpleName())));
    }

    private OAuth2User process(OAuth2UserInfo oAuth2User) {
        UserSecurityOutputAppVo user;

        try {
            user = getUserSecurityByUsernameQuery.execute(
                oAuth2User.email()
                    .map(mail -> StringUtils.isBlank(mail) ? null : mail)
                    .orElseThrow(() -> new IllegalStateException(
                        "Email has not to be blanc"
                    ))
            );
        } catch (UserNotFoundException e) {
            user = registerUserSecurityCommand.execute(new UserSecurityInputAppVo(
                oAuth2User.email().orElseThrow(() -> new IllegalStateException("Email has not to be blanc")),
                new byte[0],
                oAuth2User.firstName().orElse("") + " " + oAuth2User.lastName().orElse(""),
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
        }

        return userSecurityDetailsMapper.map(user);
    }
}
