package cmahy.simple.spring.security.common.impl.rsa.service;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.security.common.impl.rsa.exception.IllegalIOContentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ResourceLoader;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class NormalizePEMFileRSAKeyTest {

    @Mock
    private ResourceLoader resourceLoader;

    @InjectMocks
    private NormalizePEMFileRSAKey normalizePEMFileRsaKey;

    @Test
    void executePublicKey() {
        assertDoesNotThrow(() -> {
            String expectedFinalResult = Generator.generateAString(Generator.randomInt(500, 10000));

            String encodedAsPEMFileContent = "-----BEGIN PUBLIC KEY-----" +
                String.join(
                    "\r\n",
                    splitInChunks(
                        Base64.getEncoder().encodeToString(expectedFinalResult.getBytes(StandardCharsets.UTF_8)),
                        Generator.randomInt(50, 100)
                    )
                ) +
                "-----END PUBLIC KEY-----";


            byte[] actual = normalizePEMFileRsaKey.execute(encodedAsPEMFileContent.getBytes(StandardCharsets.UTF_8));


            assertThat(actual)
                .isNotNull()
                .isNotEmpty();

            assertThat(new String(actual, StandardCharsets.UTF_8))
                .isEqualTo(expectedFinalResult);
        });
    }

    @Test
    void executePrivateKey() {
        assertDoesNotThrow(() -> {
            String expectedFinalResult = Generator.generateAString(Generator.randomInt(500, 10000));

            String encodedAsPEMFileContent = "-----BEGIN PRIVATE KEY-----" +
                String.join(
                    "\r\n",
                    splitInChunks(
                        Base64.getEncoder().encodeToString(expectedFinalResult.getBytes(StandardCharsets.UTF_8)),
                        Generator.randomInt(50, 100)
                    )
                ) +
                "-----END PRIVATE KEY-----";


            byte[] actual = normalizePEMFileRsaKey.execute(encodedAsPEMFileContent.getBytes(StandardCharsets.UTF_8));


            assertThat(actual)
                .isNotNull()
                .isNotEmpty();

            assertThat(new String(actual, StandardCharsets.UTF_8))
                .isEqualTo(expectedFinalResult);
        });
    }

    private Collection<String> splitInChunks(String content, int chunkSize) {
        List<String> chunks = new ArrayList<>();

        int length = content.length();

        for (int i = 0; i < length; i += chunkSize) {
            chunks.add(content.substring(i, Math.min(length, i + chunkSize)));
        }

        return chunks;
    }

    @Test
    void execute_whenGivenByteArrayIsNull_thenThrowIllegalIOContentException() {
        assertThrows(IllegalIOContentException.class, () -> {


            normalizePEMFileRsaKey.execute(null);


        });
    }
}