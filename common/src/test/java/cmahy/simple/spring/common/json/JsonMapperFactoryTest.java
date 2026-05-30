package cmahy.simple.spring.common.json;

import cmahy.simple.spring.common.helper.Generator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JsonMapperFactoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(JsonMapperFactoryTest.class);

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            assertThat(JsonMapperFactory.create()).isNotNull();
        });
    }

    @Test
    void healthCheck() {
        assertDoesNotThrow(() -> {
            ObjectMapper mapper = JsonMapperFactory.create();

            TestVo original = new TestVo(
                UUID.randomUUID(),
                Optional.of(Generator.generateAString()),
                LocalDate.now()
            );

            String json = mapper.writeValueAsString(original);

            LOG.info("Json <{}>", json);


            TestVo restored = mapper.readValue(json, TestVo.class);


            assertThat(restored.id()).isEqualTo(original.id());
            assertThat(restored.date()).isEqualTo(original.date());
            assertThat(restored.optionalValue()).isEqualTo(original.optionalValue());
        });
    }

    private record TestVo(
        UUID id,
        Optional<String> optionalValue,
        LocalDate date
    ) {

        @Override
        public String toString() {

            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id())
                .append("optionalValue", optionalValue())
                .append("date", date())
                .toString();

        }

    }
}