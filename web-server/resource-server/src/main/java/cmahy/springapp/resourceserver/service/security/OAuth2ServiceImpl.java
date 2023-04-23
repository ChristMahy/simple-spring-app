package cmahy.springapp.resourceserver.service.security;

import cmahy.springapp.resourceserver.config.security.UserSecurityDetails;
import cmahy.springapp.resourceserver.domain.User;
import cmahy.springapp.resourceserver.security.common.AuthProvider;
import cmahy.springapp.resourceserver.security.common.OAuth2UserFactory;
import cmahy.springapp.resourceserver.security.oauth2.model.OAuth2UserInfo;
import cmahy.springapp.resourceserver.service.GetUserByUsernameService;
import cmahy.springapp.resourceserver.service.RegisterUserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OAuth2ServiceImpl extends DefaultOAuth2UserService {
    private final GetUserByUsernameService userCredentialService;
    private final RegisterUserService registerUserService;

    public OAuth2ServiceImpl(GetUserByUsernameService userCredentialService, RegisterUserService registerUserService) {
        this.userCredentialService = userCredentialService;
        this.registerUserService = registerUserService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserFactory.getOAuth2User(userRequest);

        return processUser(oAuth2UserInfo);
    }

    private UserSecurityDetails processUser(OAuth2UserInfo userInfo) {
        User user;

        try {
            user = userCredentialService.execute(userInfo.getEmail());
        } catch (UsernameNotFoundException notFound) {
            user = new User(
                null,
                userInfo.getEmail(),
                "",
                userInfo.getFirstName() + " " + userInfo.getLastName(),
                "",
                "",
                "",
                "",
                "",
                AuthProvider.GOOGLE,
                false,
                false,
                true,
                false,
                List.of()
            );

            user = registerUserService.execute(user);
        }

        return new UserSecurityDetails(user, userInfo);
    }
}
