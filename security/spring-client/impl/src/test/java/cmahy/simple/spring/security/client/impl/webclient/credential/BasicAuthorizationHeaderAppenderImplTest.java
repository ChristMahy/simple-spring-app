package cmahy.simple.spring.security.client.impl.webclient.credential;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.security.client.api.webclient.repository.BasicCredentialRepository;
import cmahy.simple.spring.security.client.api.webclient.vo.output.BasicCredentialOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static cmahy.simple.spring.security.client.impl.webclient.credential.BasicAuthorizationHeaderAppenderImpl.DEFAULT_USERNAME_OR_PASSWORD_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasicAuthorizationHeaderAppenderImplTest {

    @Mock
    private BasicCredentialRepository basicCredentialRepository;

    @InjectMocks
    private BasicAuthorizationHeaderAppenderImpl basicAuthorizationHeaderAppenderImpl;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            HttpHeaders headers = mock(HttpHeaders.class);
            BasicCredentialOutputVo basicCredentialOutputVo = mock(BasicCredentialOutputVo.class);

            String username = Generator.generateAString(200);
            String password = Generator.generateAString(200);

            when(basicCredentialOutputVo.usernameAsString()).thenReturn(Optional.of(username));
            when(basicCredentialOutputVo.passwordAsString()).thenReturn(Optional.of(password));

            when(basicCredentialRepository.getOne()).thenReturn(Optional.of(basicCredentialOutputVo));


            basicAuthorizationHeaderAppenderImpl.execute(headers);


            verify(headers).setBasicAuth(username, password, StandardCharsets.UTF_8);
            verifyNoMoreInteractions(headers);
        });
    }

    @Test
    void execute_whenPasswordIsEmpty_thenReplaceWithDefaultValue() {
        assertDoesNotThrow(() -> {
            HttpHeaders headers = mock(HttpHeaders.class);
            BasicCredentialOutputVo basicCredentialOutputVo = mock(BasicCredentialOutputVo.class);

            String username = Generator.generateAString(200);

            when(basicCredentialOutputVo.usernameAsString()).thenReturn(Optional.of(username));
            when(basicCredentialOutputVo.passwordAsString()).thenReturn(Optional.empty());

            when(basicCredentialRepository.getOne()).thenReturn(Optional.of(basicCredentialOutputVo));


            basicAuthorizationHeaderAppenderImpl.execute(headers);


            verify(headers).setBasicAuth(username, DEFAULT_USERNAME_OR_PASSWORD_VALUE, StandardCharsets.UTF_8);
            verifyNoMoreInteractions(headers);
        });
    }

    @ParameterizedTest(name = "Blank password <{0}>")
    @ValueSource(strings = {
        "                 ",
        "\t",
        "       \t        ",
        ""
    })
    void execute_whenPasswordIsBlank_thenReplaceWithDefaultValue(String password) {
        assertDoesNotThrow(() -> {
            HttpHeaders headers = mock(HttpHeaders.class);
            BasicCredentialOutputVo basicCredentialOutputVo = mock(BasicCredentialOutputVo.class);

            String username = Generator.generateAString(200);

            when(basicCredentialOutputVo.usernameAsString()).thenReturn(Optional.of(username));
            when(basicCredentialOutputVo.passwordAsString()).thenReturn(Optional.of(password));

            when(basicCredentialRepository.getOne()).thenReturn(Optional.of(basicCredentialOutputVo));


            basicAuthorizationHeaderAppenderImpl.execute(headers);


            verify(headers).setBasicAuth(username, DEFAULT_USERNAME_OR_PASSWORD_VALUE, StandardCharsets.UTF_8);
            verifyNoMoreInteractions(headers);
        });
    }

    @Test
    void execute_whenUsernameIsEmpty_thenReplaceWithDefaultValue() {
        assertDoesNotThrow(() -> {
            HttpHeaders headers = mock(HttpHeaders.class);
            BasicCredentialOutputVo basicCredentialOutputVo = mock(BasicCredentialOutputVo.class);

            String password = Generator.generateAString(200);

            when(basicCredentialOutputVo.usernameAsString()).thenReturn(Optional.empty());
            when(basicCredentialOutputVo.passwordAsString()).thenReturn(Optional.of(password));

            when(basicCredentialRepository.getOne()).thenReturn(Optional.of(basicCredentialOutputVo));


            basicAuthorizationHeaderAppenderImpl.execute(headers);


            verify(headers).setBasicAuth(DEFAULT_USERNAME_OR_PASSWORD_VALUE, password, StandardCharsets.UTF_8);
            verifyNoMoreInteractions(headers);
        });
    }

    @ParameterizedTest(name = "Blank username <{0}>")
    @ValueSource(strings = {
        "                 ",
        "\t",
        "       \t        ",
        ""
    })
    void execute_whenUsernameIsBlank_thenReplaceWithDefaultValue(String username) {
        assertDoesNotThrow(() -> {
            HttpHeaders headers = mock(HttpHeaders.class);
            BasicCredentialOutputVo basicCredentialOutputVo = mock(BasicCredentialOutputVo.class);

            String password = Generator.generateAString(200);

            when(basicCredentialOutputVo.usernameAsString()).thenReturn(Optional.of(username));
            when(basicCredentialOutputVo.passwordAsString()).thenReturn(Optional.of(password));

            when(basicCredentialRepository.getOne()).thenReturn(Optional.of(basicCredentialOutputVo));


            basicAuthorizationHeaderAppenderImpl.execute(headers);


            verify(headers).setBasicAuth(DEFAULT_USERNAME_OR_PASSWORD_VALUE, password, StandardCharsets.UTF_8);
            verifyNoMoreInteractions(headers);
        });
    }

    @Test
    void execute_whenUsernameAndPasswordAreEmpty_thenHeaderHasNoBasicAuth() {
        assertDoesNotThrow(() -> {
            HttpHeaders headers = mock(HttpHeaders.class);

            when(basicCredentialRepository.getOne()).thenReturn(Optional.empty());


            basicAuthorizationHeaderAppenderImpl.execute(headers);


            verifyNoInteractions(headers);
        });
    }

    @ParameterizedTest(name = "Blank username and password <{0}>")
    @ValueSource(strings = {
        "                 ",
        "\t",
        "       \t        ",
        ""
    })
    void execute_whenUsernameAndPasswordAreBlank_thenHeaderHasNoBasicAuth(String usernameAndPassword) {
        assertDoesNotThrow(() -> {
            HttpHeaders headers = mock(HttpHeaders.class);
            BasicCredentialOutputVo basicCredentialOutputVo = mock(BasicCredentialOutputVo.class);

            when(basicCredentialOutputVo.usernameAsString()).thenReturn(Optional.of(usernameAndPassword));
            when(basicCredentialOutputVo.passwordAsString()).thenReturn(Optional.of(usernameAndPassword));

            when(basicCredentialRepository.getOne()).thenReturn(Optional.of(basicCredentialOutputVo));


            basicAuthorizationHeaderAppenderImpl.execute(headers);


            verifyNoInteractions(headers);
        });
    }
}