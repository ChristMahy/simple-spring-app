package cmahy.simple.spring.webapp.resource.impl.adapter.config;

import cmahy.simple.spring.common.helper.Generator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class JsonMapperConfigurerIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(JsonMapperConfigurerIntegrationTest.class);

    @Autowired
    private ObjectMapper mapper;

    @Test
    void healthCheckWithSpecialTypes() {
        assertDoesNotThrow(() -> {

            TestWithAddOnVo original = new TestWithAddOnVo(
                UUID.randomUUID(),
                Optional.of(Generator.generateAString()),
                LocalDate.now(),
                new BigDecimal("1000000000000000000000000000000000"),
                Generator.generateAString()
            );


            String json = mapper.writeValueAsString(original);


            assertThat(json).contains(original.id().toString());
            assertThat(json).contains(original.optionalValue().get());
            assertThat(json).contains(original.date().format(DateTimeFormatter.ISO_DATE));
            assertThat(json).contains(original.bigDecimal().toPlainString());
            LOG.info("Json <{}>", json);


            TestVo restored = mapper.readValue(json, TestVo.class);


            assertThat(restored.id()).isEqualTo(original.id());
            assertThat(restored.date()).isEqualTo(original.date());
            assertThat(restored.optionalValue()).isEqualTo(original.optionalValue());
            assertThat(restored.bigDecimal()).usingComparator(BigDecimal::compareTo).isEqualTo(original.bigDecimal());

        });
    }

    private record TestWithAddOnVo(
        UUID id,
        Optional<String> optionalValue,
        LocalDate date,
        BigDecimal bigDecimal,
        String addOn
    ) {

        @Override
        public String toString() {

            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id())
                .append("optionalValue", optionalValue())
                .append("date", date())
                .append("bigDecimal", bigDecimal())
                .append("addOn", addOn())
                .toString();

        }

    }

    private record TestVo(
        UUID id,
        Optional<String> optionalValue,
        LocalDate date,
        BigDecimal bigDecimal
    ) {

        @Override
        public String toString() {

            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id())
                .append("optionalValue", optionalValue())
                .append("date", date())
                .append("bigDecimal", bigDecimal())
                .toString();

        }

    }
    
}