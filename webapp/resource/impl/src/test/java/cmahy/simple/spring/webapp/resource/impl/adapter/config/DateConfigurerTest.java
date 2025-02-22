package cmahy.simple.spring.webapp.resource.impl.adapter.config;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateConfigurerTest {

    @InjectMocks
    private DateConfigurer dateConfigurer;

    @Test
    void yearMonthDayHoursMinutesSeconds() {
        assertDoesNotThrow(() -> {
            var dateTimeFormatter = dateConfigurer.yearMonthDayHoursMinutesSeconds();
            var now = LocalDateTime.now(ZoneId.of("Europe/Brussels"));

            assertThat(now.format(dateTimeFormatter)).isEqualTo(String.format(
                "%d%s%s%s%s%s",
                now.getYear(),
                StringUtils.leftPad(String.valueOf(now.getMonthValue()), 2, "0"),
                StringUtils.leftPad(String.valueOf(now.getDayOfMonth()), 2, "0"),
                StringUtils.leftPad(String.valueOf(now.getHour()), 2, "0"),
                StringUtils.leftPad(String.valueOf(now.getMinute()), 2, "0"),
                StringUtils.leftPad(String.valueOf(now.getSecond()), 2, "0")
            ));
        });
    }
}