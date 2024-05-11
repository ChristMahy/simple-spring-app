package cmahy.webapp.resource.impl.adapter.security.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.adapter.security.mapper.output.UserSecurityDetailsMapper;
import cmahy.webapp.resource.impl.adapter.security.service.factory.OAuth2UserInfoFactory;
import cmahy.webapp.resource.impl.adapter.security.vo.google.output.OAuth2UserInfo;
import cmahy.webapp.resource.impl.application.user.command.RegisterUserSecurityCommand;
import cmahy.webapp.resource.impl.application.user.query.GetUserSecurityByUsernameQuery;
import cmahy.webapp.resource.impl.application.user.vo.input.UserSecurityInputAppVo;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.exception.user.*;
import cmahy.webapp.resource.user.api.security.vo.output.UserSecurityDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuth2ServiceImplTest {

    @Mock
    private OAuth2UserInfoFactory oAuth2UserInfoFactory;

    @Mock
    private GetUserSecurityByUsernameQuery getUserSecurityByUsernameQuery;

    @Mock
    private RegisterUserSecurityCommand registerUserSecurityCommand;

    @Mock
    private UserSecurityDetailsMapper userSecurityDetailsMapper;

    @InjectMocks
    private OAuth2ServiceImpl oAuth2ServiceImpl;

    @Test
    void loadUser() {
        assertDoesNotThrow(() -> {
            OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class, RETURNS_DEEP_STUBS);
            OAuth2UserInfo oAuth2UserInfo = mock(OAuth2UserInfo.class);
            UserSecurityOutputAppVo userSecurityOutputAppVo = mock(UserSecurityOutputAppVo.class);
            UserSecurityDetails userSecurityDetails = mock(UserSecurityDetails.class);

            when(oAuth2UserRequest.getClientRegistration().getRegistrationId()).thenReturn(AuthProvider.GOOGLE.name());

            String email = Generator.generateAString();
            when(oAuth2UserInfo.email()).thenReturn(Optional.of(email));

            when(oAuth2UserInfoFactory.create(oAuth2UserRequest)).thenReturn(Optional.of(oAuth2UserInfo));
            when(getUserSecurityByUsernameQuery.execute(email, AuthProvider.GOOGLE)).thenReturn(userSecurityOutputAppVo);
            when(userSecurityDetailsMapper.map(userSecurityOutputAppVo)).thenReturn(userSecurityDetails);

            OAuth2User actual = oAuth2ServiceImpl.loadUser(oAuth2UserRequest);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(userSecurityDetails);

            verifyNoInteractions(registerUserSecurityCommand);
        });
    }

    @Test
    void loadUser_onUserNameNotFound_thenRegisterItAndReturnUser() {
        assertDoesNotThrow(() -> {
            OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class, RETURNS_DEEP_STUBS);
            OAuth2UserInfo oAuth2UserInfo = mock(OAuth2UserInfo.class);
            UserSecurityOutputAppVo userSecurityOutputAppVo = mock(UserSecurityOutputAppVo.class);
            UserSecurityDetails userSecurityDetails = mock(UserSecurityDetails.class);

            when(oAuth2UserRequest.getClientRegistration().getRegistrationId()).thenReturn("google");

            String email = Generator.generateAString();
            when(oAuth2UserInfo.email()).thenReturn(Optional.of(email));

            when(oAuth2UserInfoFactory.create(oAuth2UserRequest)).thenReturn(Optional.of(oAuth2UserInfo));
            when(getUserSecurityByUsernameQuery.execute(email, AuthProvider.GOOGLE)).thenThrow(UserNotFoundException.class);
            when(registerUserSecurityCommand.execute(any(UserSecurityInputAppVo.class))).thenReturn(userSecurityOutputAppVo);
            when(userSecurityDetailsMapper.map(userSecurityOutputAppVo)).thenReturn(userSecurityDetails);

            OAuth2User actual = oAuth2ServiceImpl.loadUser(oAuth2UserRequest);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(userSecurityDetails);
        });
    }

    @Test
    void loadUser_onRegisterNewUserButExists_thenThrowAuthenticationException() {
        OAuth2AuthenticationException oAuth2AuthenticationException = assertThrows(OAuth2AuthenticationException.class, () -> {
            OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class, RETURNS_DEEP_STUBS);
            OAuth2UserInfo oAuth2UserInfo = mock(OAuth2UserInfo.class);

            when(oAuth2UserRequest.getClientRegistration().getRegistrationId()).thenReturn("google");

            String email = Generator.generateAString();
            when(oAuth2UserInfo.email()).thenReturn(Optional.of(email));

            when(oAuth2UserInfoFactory.create(oAuth2UserRequest)).thenReturn(Optional.of(oAuth2UserInfo));
            when(getUserSecurityByUsernameQuery.execute(email, AuthProvider.GOOGLE)).thenThrow(UserNotFoundException.class);
            when(registerUserSecurityCommand.execute(any(UserSecurityInputAppVo.class))).thenThrow(UserExistsException.class);

            oAuth2ServiceImpl.loadUser(oAuth2UserRequest);
        });

        assertThat(oAuth2AuthenticationException.getError().getErrorCode()).isEqualTo("user-exists");
    }

    @Test
    void loadUser_onAnyOtherException_thenThrowAuthenticationException() {
        String errorMessage = Generator.generateAString();

        OAuth2AuthenticationException oAuth2AuthenticationException = assertThrows(OAuth2AuthenticationException.class, () -> {
            OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class, RETURNS_DEEP_STUBS);
            OAuth2UserInfo oAuth2UserInfo = mock(OAuth2UserInfo.class);

            when(oAuth2UserRequest.getClientRegistration().getRegistrationId()).thenReturn("google");

            String email = Generator.generateAString();
            when(oAuth2UserInfo.email()).thenReturn(Optional.of(email));

            when(oAuth2UserInfoFactory.create(oAuth2UserRequest)).thenReturn(Optional.of(oAuth2UserInfo));
            when(getUserSecurityByUsernameQuery.execute(email, AuthProvider.GOOGLE)).thenThrow(UserNotFoundException.class);
            when(registerUserSecurityCommand.execute(any(UserSecurityInputAppVo.class))).thenAnswer(invocationOnMock -> {
                throw new Exception(errorMessage);
            });

            oAuth2ServiceImpl.loadUser(oAuth2UserRequest);
        });

        assertThat(oAuth2AuthenticationException.getError().getErrorCode()).isEqualTo("error-registration");
        assertThat(oAuth2AuthenticationException.getCause().getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void loadUser_onUserInfoMailIsBlank_thenThrowException() {
        assertThrows(UserValidationException.class, () -> {
            OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class, RETURNS_DEEP_STUBS);
            OAuth2UserInfo oAuth2UserInfo = mock(OAuth2UserInfo.class);

            when(oAuth2UserRequest.getClientRegistration().getRegistrationId()).thenReturn("google");

            when(oAuth2UserInfo.email()).thenReturn(Optional.of("                     "));

            when(oAuth2UserInfoFactory.create(oAuth2UserRequest)).thenReturn(Optional.of(oAuth2UserInfo));

            oAuth2ServiceImpl.loadUser(oAuth2UserRequest);
        });

        verifyNoInteractions(getUserSecurityByUsernameQuery, registerUserSecurityCommand);
    }

    @Test
    void loadUser_onUserInfoIsNull_thenThrowException() {
        assertThrows(UserNotFoundException.class, () -> {
            OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class);

            when(oAuth2UserInfoFactory.create(oAuth2UserRequest)).thenReturn(Optional.empty());

            oAuth2ServiceImpl.loadUser(oAuth2UserRequest);
        });

        verifyNoInteractions(getUserSecurityByUsernameQuery, registerUserSecurityCommand);
    }


}