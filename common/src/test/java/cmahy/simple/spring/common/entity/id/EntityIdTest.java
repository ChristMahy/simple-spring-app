package cmahy.simple.spring.common.entity.id;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.common.json.JsonMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EntityIdTest {

    private ObjectMapper objectMapper = JsonMapperFactory.create();

    @Test
    void wrap() {
        assertDoesNotThrow(() -> {

            String id = Generator.generateAStringWithoutSpecialChars();
            FakeEntityId fakeEntityId = new FakeEntityId(id);


            String actual = objectMapper.writeValueAsString(fakeEntityId);


            assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("{\"id\":\"" + id + "\"}");
        });
    }

    @Test
    void unwrap() {
        assertDoesNotThrow(() -> {

            String id = Generator.generateAStringWithoutSpecialChars();
            String json = "{\"id\":\"" + id + "\"}";


            FakeEntityId actual = objectMapper.readValue(json, FakeEntityId.class);


            assertThat(actual)
                .isNotNull()
                .extracting(FakeEntityId::value)
                .isEqualTo(id);
        });
    }

    private record FakeEntityId(String value) implements EntityId<String> {}
}