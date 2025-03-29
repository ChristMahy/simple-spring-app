package cmahy.simple.spring.common.entity.id;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.common.json.JsonMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EntityIdTest {

    private ObjectMapper objectMapper = JsonMapperFactory.create();

    @Test
    void serialize() {
        assertDoesNotThrow(() -> {

            String stringId = Generator.generateAStringWithoutSpecialChars();
            FakeEntityString fakeEntityString = new FakeEntityString(stringId);

            UUID uuidId = Generator.randomUUID();
            FakeEntityUuid fakeEntityUuid = new FakeEntityUuid(uuidId);

            Long longId = Generator.randomLong();
            FakeEntityLong fakeEntityLong = new FakeEntityLong(longId);

            FakeEntityWrapper wrapper = new FakeEntityWrapper(
                fakeEntityString,
                fakeEntityUuid,
                fakeEntityLong
            );


            String actual = objectMapper.writeValueAsString(wrapper);


            assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(
                    "{" +
                        "\"stringId\":\"" + stringId + "\"," +
                        "\"uuidId\":\"" + uuidId + "\"," +
                        "\"longId\":" + longId +
                        "}"
                );
        });
    }

    @Test
    void deserialize() {
        assertDoesNotThrow(() -> {

            String stringId = Generator.generateAStringWithoutSpecialChars();
            UUID uuidId = Generator.randomUUID();
            Long longId = Generator.randomLong();

            String json = "{" +
                "\"stringId\":\"" + stringId + "\"," +
                "\"uuidId\":\"" + uuidId + "\"," +
                "\"longId\":" + longId +
                "}";


            FakeEntityWrapper actual = objectMapper.readValue(json, FakeEntityWrapper.class);


            assertThat(actual).isNotNull();

            assertThat(actual.stringId())
                .isNotNull()
                .extracting(FakeEntityString::value)
                .isEqualTo(stringId);


            assertThat(actual.uuidId())
                .isNotNull()
                .extracting(FakeEntityUuid::value)
                .isEqualTo(uuidId);


            assertThat(actual.longId())
                .isNotNull()
                .extracting(FakeEntityLong::value)
                .isEqualTo(longId);

        });
    }

    public record FakeEntityWrapper(
        FakeEntityString stringId,
        FakeEntityUuid uuidId,
        FakeEntityLong longId
    ) {
    }

    public record FakeEntityString(String value) implements EntityId<String> {
    }

    public record FakeEntityUuid(UUID value) implements EntityId<UUID> {
    }

    public record FakeEntityLong(Long value) implements EntityId<Long> {
    }
}