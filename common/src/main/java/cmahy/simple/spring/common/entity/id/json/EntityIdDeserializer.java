package cmahy.simple.spring.common.entity.id.json;

import cmahy.simple.spring.common.entity.exception.NoSuitableConstructorFoundException;
import cmahy.simple.spring.common.entity.id.EntityId;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class EntityIdDeserializer extends StdDeserializer<EntityId<?>> implements ContextualDeserializer {

    public EntityIdDeserializer() {
        this(null);
    }

    public EntityIdDeserializer(JavaType valueType) {
        super(valueType);
    }

    @Override
    public JsonDeserializer<?> createContextual(
        DeserializationContext deserializationContext, BeanProperty beanProperty
    ) {
        return new EntityIdDeserializer(deserializationContext.getContextualType());
    }

    @Override
    public EntityId<?> deserialize(
        JsonParser jsonParser,
        DeserializationContext deserializationContext
    ) throws IOException {
        Class<?> rawClass = this.getValueType().getRawClass();

        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Constructor<?> constructor = Arrays.stream(rawClass.getDeclaredConstructors())
            .filter(c -> c.getModifiers() != Modifier.PRIVATE && c.getParameterCount() == 1)
            .findFirst()
            .orElseThrow(() -> new NoSuitableConstructorFoundException("Constructor not found, no (public or protected) suitable constructor"));

        try {
            return (EntityId<?>) constructor.newInstance(
                deserializationContext.readTreeAsValue(node, constructor.getParameterTypes()[0])
            );
        } catch (Exception any) {
            throw new NoSuitableConstructorFoundException(any.getMessage(), any);
        }
    }
}
