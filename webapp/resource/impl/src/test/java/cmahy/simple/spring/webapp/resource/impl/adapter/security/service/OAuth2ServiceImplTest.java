package cmahy.simple.spring.webapp.resource.impl.adapter.security.service;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.mapper.output.UserSecurityDetailsMapper;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.service.factory.OAuth2UserInfoFactory;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.vo.google.output.OAuth2UserInfo;
import cmahy.simple.spring.webapp.resource.ui.vo.output.UserSecurityDetails;
import cmahy.simple.spring.webapp.user.kernel.application.command.RegisterUserSecurityCommand;
import cmahy.simple.spring.webapp.user.kernel.application.query.GetUserSecurityByUsernameQuery;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.exception.*;
import cmahy.simple.spring.webapp.user.kernel.vo.input.UserSecurityInputAppVo;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            when(registerUserSecurityCommand.execute(any(UserSecurityInputAppVo.class))).thenAnswer(_ -> {
                throw new Exception(errorMessage);
            });

            oAuth2ServiceImpl.loadUser(oAuth2UserRequest);
        });

        assertThat(oAuth2AuthenticationException.getError().getErrorCode()).isEqualTo("error-registration");
        assertThat(oAuth2AuthenticationException.getCause().getMessage()).isEqualTo(errorMessage);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"           ", "\t"})
    void loadUser_onUserInfoMailIsBlank_thenThrowException(String mail) {
        OAuth2AuthenticationException oAuth2AuthenticationException = assertThrows(OAuth2AuthenticationException.class, () -> {
            OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class, RETURNS_DEEP_STUBS);
            OAuth2UserInfo oAuth2UserInfo = mock(OAuth2UserInfo.class);

            when(oAuth2UserRequest.getClientRegistration().getRegistrationId()).thenReturn("google");

            when(oAuth2UserInfo.email()).thenReturn(Optional.ofNullable(mail));

            when(oAuth2UserInfoFactory.create(oAuth2UserRequest)).thenReturn(Optional.of(oAuth2UserInfo));

            oAuth2ServiceImpl.loadUser(oAuth2UserRequest);
        });

        assertThat(oAuth2AuthenticationException).hasCauseExactlyInstanceOf(UserValidationException.class);

        verifyNoInteractions(getUserSecurityByUsernameQuery, registerUserSecurityCommand);
    }

    @Test
    void loadUser_onUserInfoIsNull_thenThrowException() {
        assertThrows(UsernameNotFoundException.class, () -> {
            OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class);

            when(oAuth2UserInfoFactory.create(oAuth2UserRequest)).thenReturn(Optional.empty());

            oAuth2ServiceImpl.loadUser(oAuth2UserRequest);
        });

        verifyNoInteractions(getUserSecurityByUsernameQuery, registerUserSecurityCommand);
    }


}