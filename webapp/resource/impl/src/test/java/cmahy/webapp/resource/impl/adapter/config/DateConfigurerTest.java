package cmahy.webapp.resource.impl.adapter.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

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

            assertThat(now.format(dateTimeFormatter)).isEqualTo(String.format("%d%d%d%d%d%d", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond()));
        });
    }
}