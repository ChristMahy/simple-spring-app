package cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.google;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.config.properties.GoogleProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.google.vo.output.GoogleProfileImpl;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.vo.input.UserInfoRequestConfig;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.vo.google.output.OAuth2UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoogleGetUserInfoOauth2ServiceImplTest {

    @Mock
    private GoogleProperties googleProperties;

    @Mock
    private GoogleUserInfoRequestFactory googleUserInfoRequestFactory;

    @InjectMocks
    private GoogleGetUserInfoOauth2ServiceImpl googleGetUserInfoOauth2ServiceImpl;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            RestTemplate restTemplate = mock(RestTemplate.class);
            UserInfoRequestConfig configs = new UserInfoRequestConfig(
                Optional.of(Generator.generateAString())
            );
            String apiUrl = String.format(
                "https://%s.%s%s.%s",
                Generator.generateAStringWithoutSpecialChars(3),
                "SHOULD_NOT_BE_CALLED_",
                Generator.generateAStringWithoutSpecialChars(),
                Generator.generateAStringWithoutSpecialChars(3)
            );
            GoogleProfileImpl googleProfile = new GoogleProfileImpl(
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString()
            );

            when(googleProperties.apiUrl()).thenReturn(apiUrl);
            when(googleUserInfoRequestFactory.create(configs)).thenReturn(restTemplate);

            when(restTemplate.getForObject(apiUrl, GoogleProfileImpl.class)).thenReturn(googleProfile);

            Optional<OAuth2UserInfo> actual = googleGetUserInfoOauth2ServiceImpl.execute(configs);

            assertThat(actual).isNotEmpty();

            assertThat(actual.get().id())
                .isNotEmpty()
                .hasValue(googleProfile.sub());
            assertThat(actual.get().email())
                .isNotEmpty()
                .hasValue(googleProfile.email());
            assertThat(actual.get().attribute("name"))
                .isNotEmpty()
                .hasValue(googleProfile.name());
            assertThat(actual.get().firstName())
                .isNotEmpty()
                .hasValue(googleProfile.given_name());
            assertThat(actual.get().lastName())
                .isNotEmpty()
                .hasValue(googleProfile.family_name());
        });
    }

    @Test
    void execute_whenValueReceivedFromRequestIsNull_thenReturnEmptyValue() {
        assertDoesNotThrow(() -> {
            RestTemplate restTemplate = mock(RestTemplate.class);
            UserInfoRequestConfig configs = new UserInfoRequestConfig(
                Optional.of(Generator.generateAString())
            );
            String apiUrl = String.format(
                "https://%s.%s%s.%s",
                Generator.generateAStringWithoutSpecialChars(3),
                "SHOULD_NOT_BE_CALLED_",
                Generator.generateAStringWithoutSpecialChars(),
                Generator.generateAStringWithoutSpecialChars(3)
            );

            when(googleProperties.apiUrl()).thenReturn(apiUrl);
            when(googleUserInfoRequestFactory.create(configs)).thenReturn(restTemplate);

            when(restTemplate.getForObject(apiUrl, GoogleProfileImpl.class)).thenReturn(null);

            Optional<OAuth2UserInfo> actual = googleGetUserInfoOauth2ServiceImpl.execute(configs);

            assertThat(actual).isEmpty();
        });
    }
}