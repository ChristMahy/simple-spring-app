package cmahy.simple.spring.security.webclient.api.vo.output;

import cmahy.simple.spring.common.helper.Generator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BasicCredentialOutputVoTest {

    private BasicCredentialOutputVo basicCredentialOutputVo;

    @Test
    void usernameAsString() {
        assertDoesNotThrow(() -> {

            String username = Generator.generateAString(500);

            basicCredentialOutputVo = new BasicCredentialOutputVo(
                Optional.of(username.getBytes(StandardCharsets.UTF_8)),
                Optional.empty()
            );


            Optional<String> actual = basicCredentialOutputVo.usernameAsString();


            assertThat(actual)
                .isNotEmpty()
                .hasValue(username);
        });
    }

    @Test
    void passwordAsString() {
        assertDoesNotThrow(() -> {

            String password = Generator.generateAString(500);

            basicCredentialOutputVo = new BasicCredentialOutputVo(
                Optional.empty(),
                Optional.of(password.getBytes(StandardCharsets.UTF_8))
            );


            Optional<String> actual = basicCredentialOutputVo.passwordAsString();


            assertThat(actual)
                .isNotEmpty()
                .hasValue(password);
        });
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("charsets")
    void encodedAsByteWithbadCharset_givenEqualityFailure(Charset charset) {
        assertDoesNotThrow(() -> {

            String username = Generator.generateAString(500);
            String password = Generator.generateAString(500);

            basicCredentialOutputVo = new BasicCredentialOutputVo(
                Optional.of(username.getBytes(charset)),
                Optional.of(password.getBytes(charset))
            );


            Optional<String> actualUserName = basicCredentialOutputVo.usernameAsString();
            Optional<String> actualPassword = basicCredentialOutputVo.passwordAsString();


            assertThat(actualUserName).isNotEmpty();
            assertThat(actualUserName.get()).isNotEqualTo(username);

            assertThat(actualPassword).isNotEmpty();
            assertThat(actualPassword.get()).isNotEqualTo(password);
        });
    }

    public static Stream<Charset> charsets() {
        return Stream.of(
            StandardCharsets.UTF_16,
            StandardCharsets.UTF_16BE,
            StandardCharsets.UTF_16LE,
            StandardCharsets.UTF_32,
            StandardCharsets.UTF_32BE,
            StandardCharsets.UTF_32LE
        );
    }
}