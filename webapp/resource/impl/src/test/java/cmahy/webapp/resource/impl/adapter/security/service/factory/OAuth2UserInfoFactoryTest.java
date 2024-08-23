package cmahy.webapp.resource.impl.adapter.security.service.factory;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.adapter.security.request.userinfo.GetUserInfoOauth2Service;
import cmahy.webapp.resource.impl.adapter.security.request.userinfo.vo.input.UserInfoRequestConfig;
import cmahy.webapp.resource.impl.adapter.security.vo.google.output.OAuth2UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuth2UserInfoFactoryTest {

    @Mock
    private GetUserInfoOauth2Service getUserInfoOauth2Service;

    @InjectMocks
    private OAuth2UserInfoFactory oAuth2UserInfoFactory;

    @Test
    void createGoogle() {
        assertDoesNotThrow(() -> {
            OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class, RETURNS_DEEP_STUBS);
            String accessToken = Generator.generateAString();
            UserInfoRequestConfig userInfoRequestConfig = new UserInfoRequestConfig(Optional.of(accessToken));
            OAuth2UserInfo oAuth2UserInfo = mock(OAuth2UserInfo.class);

            when(oAuth2UserRequest.getClientRegistration().getRegistrationId()).thenReturn("google");
            when(oAuth2UserRequest.getAccessToken().getTokenValue()).thenReturn(accessToken);
            when(getUserInfoOauth2Service.execute(userInfoRequestConfig)).thenReturn(Optional.of(oAuth2UserInfo));

            Optional<OAuth2UserInfo> actual = oAuth2UserInfoFactory.create(oAuth2UserRequest);

            assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .hasValue(oAuth2UserInfo);
        });
    }

    @Test
    void create_whenUnknownProvider_thenReturnEmptyValue() {
        assertDoesNotThrow(() -> {
            OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class, RETURNS_DEEP_STUBS);

            when(oAuth2UserRequest.getClientRegistration().getRegistrationId()).thenReturn(Generator.generateAString(500));

            Optional<OAuth2UserInfo> actual = oAuth2UserInfoFactory.create(oAuth2UserRequest);

            assertThat(actual)
                .isNotNull()
                .isEmpty();

            verify(getUserInfoOauth2Service, never()).execute(any(UserInfoRequestConfig.class));
        });
    }
}