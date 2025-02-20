package cmahy.simple.spring.webapp.shell.client.impl.adapter.config;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class DateFormatterConfigurerTest {

    @InjectMocks
    private DateFormatterConfigurer dateFormatterConfigurer;

    @Test
    void belgiumSimpleDateFormatter() {
        assertDoesNotThrow(() -> {
            LocalDateTime localDateTime = LocalDateTime.now()
                .atZone(ZoneId.of("Europe/Brussels"))
                .toLocalDateTime();

            DateTimeFormatter actual = dateFormatterConfigurer.belgiumSimpleDateFormatter();

            assertThat(actual).isNotNull();

            String actualDateFormatted = actual.format(localDateTime);

            assertThat(actualDateFormatted)
                .isNotNull()
                .isEqualTo(String.format(
                    "%s/%s/%s %s:%s:%s",
                    StringUtils.leftPad(String.valueOf(localDateTime.getDayOfMonth()), 2, '0'),
                    StringUtils.leftPad(String.valueOf(localDateTime.getMonthValue()), 2, '0'),
                    StringUtils.leftPad(String.valueOf(localDateTime.getYear()), 4, '0'),
                    StringUtils.leftPad(String.valueOf(localDateTime.getHour()), 2, '0'),
                    StringUtils.leftPad(String.valueOf(localDateTime.getMinute()), 2, '0'),
                    StringUtils.leftPad(String.valueOf(localDateTime.getSecond()), 2, '0')
                ));
        });
    }
}