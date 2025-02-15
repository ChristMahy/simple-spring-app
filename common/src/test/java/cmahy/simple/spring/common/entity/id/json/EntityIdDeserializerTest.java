package cmahy.simple.spring.common.entity.id.json;

import cmahy.simple.spring.common.entity.exception.NoSuitableConstructorFoundException;
import cmahy.simple.spring.common.entity.id.EntityId;
import cmahy.simple.spring.common.json.JsonMapperFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static cmahy.simple.spring.common.helper.Generator.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EntityIdDeserializerTest {

    @Test
    void deserialize() {
        assertDoesNotThrow(() -> {
            ObjectMapper jsonMapper = JsonMapperFactory.create();

            SimpleModule simpleModule = new SimpleModule(
                EntityIdDeserializer.class.getSimpleName(),
                new Version(1, 0, 0, null, null, null)
            );
            simpleModule.addDeserializer(EntityId.class, new EntityIdDeserializer());
            jsonMapper.registerModule(simpleModule);

            Long idValue = randomLong();

            EntityId<Long> actual = jsonMapper.readValue(idValue.toString(), ConcreteTestEntityId.class);

            assertThat(actual).isNotNull();
            assertThat(actual.value()).isEqualTo(idValue);
        });
    }

    @ParameterizedTest
    @MethodSource("noSuitableClasses")
    void deserialize_whenNoSuitableConstructorFound_thenThrowNoSuitableConstructorFoundException(Class<? extends EntityId<Long>> entityIdClass) {
        assertThrows(NoSuitableConstructorFoundException.class, () -> {
            ObjectMapper jsonMapper = JsonMapperFactory.create();

            SimpleModule simpleModule = new SimpleModule(
                EntityIdDeserializer.class.getSimpleName(),
                new Version(1, 0, 0, null, null, null)
            );
            simpleModule.addDeserializer(EntityId.class, new EntityIdDeserializer());
            jsonMapper.registerModule(simpleModule);

            Long idValue = randomLong();

            jsonMapper.readValue(idValue.toString(), entityIdClass);
        });
    }

    private static Stream<Class<? extends EntityId<Long>>> noSuitableClasses() {
        return Stream.of(
            ConcreteTestEntityNoConstructor.class,
            ConcreteTestEntityPrivateConstructor.class,
            ConcreteTestEntityMultiArgumentsConstructor.class
        );
    }

    protected static class ConcreteTestEntityNoConstructor implements EntityId<Long> {
        @Override
        public Long value() {
            throw new IllegalStateException("Not yet implemented ! Should not be used !");
        }
    }

    protected static class ConcreteTestEntityPrivateConstructor implements EntityId<Long> {
        private ConcreteTestEntityPrivateConstructor() {}

        @Override
        public Long value() {
            throw new IllegalStateException("Not yet implemented ! Should not be used !");
        }
    }

    protected static class ConcreteTestEntityMultiArgumentsConstructor implements EntityId<Long> {
        public ConcreteTestEntityMultiArgumentsConstructor(Long param1, Long param2) {}

        @Override
        public Long value() {
            throw new IllegalStateException("Not yet implemented ! Should not be used !");
        }
    }

    @Test
    void deserialize_whenAnyErrorOnInstantiation_thenThrowNoSuitableConstructorFoundException() {
        assertDoesNotThrow(() -> {
            JsonParser jsonParser = mock(JsonParser.class, RETURNS_DEEP_STUBS);
            JsonNode jsonNode = mock(JsonNode.class);

            when(jsonParser.getCodec().readTree(jsonParser)).thenReturn(jsonNode);

            EntityIdDeserializer deserializer = new EntityIdDeserializer(SimpleType.constructUnsafe(ConcreteTestEntityId.class));

            DeserializationContext context = mock(DeserializationContext.class);

            when(context.readTreeAsValue(eq(jsonNode), (Class<?>) any())).thenAnswer(invocationOnMock -> { throw new Exception(); });

            assertThrows(NoSuitableConstructorFoundException.class, () -> {
                deserializer.deserialize(jsonParser, context);
            });

            verify(context).readTreeAsValue(eq(jsonNode), (Class<?>) any());
        });
    }

    protected record ConcreteTestEntityId(Long value) implements EntityId<Long> {}
}