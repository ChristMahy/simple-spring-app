package cmahy.common.entity.id.json;

import cmahy.common.entity.id.EntityId;
import cmahy.common.json.JsonMapperFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import java.io.*;

import static cmahy.common.helper.Generator.generateAString;
import static cmahy.common.helper.Generator.randomLongEqualOrAboveZero;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class EntityIdSerializerTest {

    @Test
    void serialize_shouldReturnJustTheValue() {
        assertDoesNotThrow(() -> {
            ObjectMapper jsonMapper = JsonMapperFactory.create();

            SimpleModule simpleModule = new SimpleModule(
                EntityIdSerializer.class.getSimpleName(),
                new Version(1, 0, 0, null, null, null)
            );
            simpleModule.addSerializer(new EntityIdSerializer(EntityId.class));
            jsonMapper.registerModule(simpleModule);

            Long idValue = randomLongEqualOrAboveZero();

            EntityId<Long> id = () -> idValue;

            try (Writer writer = new StringWriter()) {
                jsonMapper.writeValue(writer, id);

                assertThat(writer.toString())
                    .isNotNull()
                    .isNotEmpty()
                    .isEqualTo(idValue.toString());
            }
        });
    }

    @Test
    void serialize_whenValueIsNull_thenResultHasToBeEmptyOrNull() {
        assertDoesNotThrow(() -> {
            EntityIdSerializer entityIdSerializer = new EntityIdSerializer(EntityId.class);

            JsonGenerator jsonGenerator = mock(JsonGenerator.class);
            SerializerProvider serializerProvider = mock(SerializerProvider.class);

            entityIdSerializer.serialize(() -> null, jsonGenerator, serializerProvider);

            verify(jsonGenerator, never()).writeRawValue(anyString());
        });
    }

    @Test
    void serialize_whenEntityIdIsNull_thenResultHasToBeEmptyOrNull() {
        assertDoesNotThrow(() -> {
            EntityIdSerializer entityIdSerializer = new EntityIdSerializer(EntityId.class);

            JsonGenerator jsonGenerator = mock(JsonGenerator.class);
            SerializerProvider serializerProvider = mock(SerializerProvider.class);

            entityIdSerializer.serialize(null, jsonGenerator, serializerProvider);

            verify(jsonGenerator, never()).writeRawValue(anyString());
        });
    }
}