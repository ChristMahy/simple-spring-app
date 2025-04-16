package cmahy.simple.spring.security.webclient.impl.credential;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.security.webclient.api.repository.BasicCredentialRepository;
import cmahy.simple.spring.security.webclient.api.vo.output.BasicCredentialOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

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

            String username = Generator.generateAStringWithoutSpecialChars();
            String password = Generator.generateAStringWithoutSpecialChars();

            HttpHeaders httpHeaders = mock(HttpHeaders.class);

            BasicCredentialOutputVo basicCredential = mock(BasicCredentialOutputVo.class);

            when(basicCredential.usernameAsString()).thenReturn(Optional.of(username));
            when(basicCredential.passwordAsString()).thenReturn(Optional.of(password));

            when(basicCredentialRepository.getOne()).thenReturn(Optional.of(basicCredential));


            basicAuthorizationHeaderAppenderImpl.execute(httpHeaders);


            verify(httpHeaders).setBasicAuth(username, password, StandardCharsets.UTF_8);
            verifyNoMoreInteractions(httpHeaders);
        });
    }

    @Test
    void execute_whenPasswordIsNull_thenShouldAppendOnlyUsername() {
        assertDoesNotThrow(() -> {

            String username = Generator.generateAStringWithoutSpecialChars();

            HttpHeaders httpHeaders = mock(HttpHeaders.class);

            BasicCredentialOutputVo basicCredential = mock(BasicCredentialOutputVo.class);

            when(basicCredential.usernameAsString()).thenReturn(Optional.of(username));
            when(basicCredential.passwordAsString()).thenReturn(Optional.empty());

            when(basicCredentialRepository.getOne()).thenReturn(Optional.of(basicCredential));


            basicAuthorizationHeaderAppenderImpl.execute(httpHeaders);


            verify(httpHeaders).setBasicAuth(username, "", StandardCharsets.UTF_8);
        });
    }

    @Test
    void execute_whenUsernameIsNull_thenShouldAppendOnlyPassword() {
        assertDoesNotThrow(() -> {

            String password = Generator.generateAStringWithoutSpecialChars();

            HttpHeaders httpHeaders = mock(HttpHeaders.class);

            BasicCredentialOutputVo basicCredential = mock(BasicCredentialOutputVo.class);

            when(basicCredential.usernameAsString()).thenReturn(Optional.empty());
            when(basicCredential.passwordAsString()).thenReturn(Optional.of(password));

            when(basicCredentialRepository.getOne()).thenReturn(Optional.of(basicCredential));


            basicAuthorizationHeaderAppenderImpl.execute(httpHeaders);


            verify(httpHeaders).setBasicAuth("", password, StandardCharsets.UTF_8);
        });
    }

    @Test
    void execute_whenUsernameAndPasswordAreNull_thenShouldNeverAppendHeader() {
        assertDoesNotThrow(() -> {

            HttpHeaders httpHeaders = mock(HttpHeaders.class);

            BasicCredentialOutputVo basicCredential = mock(BasicCredentialOutputVo.class);

            when(basicCredential.usernameAsString()).thenReturn(Optional.empty());
            when(basicCredential.passwordAsString()).thenReturn(Optional.empty());

            when(basicCredentialRepository.getOne()).thenReturn(Optional.of(basicCredential));


            basicAuthorizationHeaderAppenderImpl.execute(httpHeaders);


            verifyNoInteractions(httpHeaders);
        });
    }
}