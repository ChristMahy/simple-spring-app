package cmahy.webapp.resource.impl.application.service;

import cmahy.common.helper.Generator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StringGeneratorServiceTest {

    @Mock
    private Random randomizer;

    @InjectMocks
    private StringGeneratorService stringGenerator;

    @Test
    void executeFixedLengthTo50() {
        assertDoesNotThrow(() -> {
            String letters = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ_+-=()*$%0123456789";

            int letterExpectedPosition = Generator.randomInt(0, letters.length() - 1);

            when(randomizer.nextInt(0, letters.length() - 1)).thenAnswer(invocationOnMock -> letterExpectedPosition);

            String actual = stringGenerator.execute();

            assertThat(actual)
                .isNotNull()
                .hasSize(50);

            for (int i = 0; i < actual.length(); i++) {
                assertThat(actual.charAt(i)).isEqualTo(letters.charAt(letterExpectedPosition));
            }

            verify(randomizer, times(50)).nextInt(0, letters.length() - 1);
        });
    }

    @Test
    void executeWithLength() {
        assertDoesNotThrow(() -> {
            int expectedLength = Generator.randomInt(1000, 10000);

            String letters = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ_+-=()*$%0123456789";

            int letterExpectedPosition = Generator.randomInt(0, letters.length() - 1);

            when(randomizer.nextInt(0, letters.length() - 1)).thenAnswer(invocationOnMock -> letterExpectedPosition);

            String actual = stringGenerator.execute(expectedLength);

            assertThat(actual)
                .isNotNull()
                .hasSize(expectedLength);

            for (int i = 0; i < actual.length(); i++) {
                assertThat(actual.charAt(i)).isEqualTo(letters.charAt(letterExpectedPosition));
            }

            verify(randomizer, times(expectedLength)).nextInt(0, letters.length() - 1);
        });
    }
}