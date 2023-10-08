package cmahy.common.entity.json;

import cmahy.common.entity.EntityId;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.lang.reflect.Constructor;
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
    ) throws JsonMappingException {
        return new EntityIdDeserializer(deserializationContext.getContextualType());
    }

    @Override
    public EntityId<?> deserialize(
        JsonParser jsonParser,
        DeserializationContext deserializationContext
    ) throws IOException, JacksonException {
        Class<?> rawClass = this.getValueType().getRawClass();

        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Constructor<?> constructor = Arrays.stream(rawClass.getConstructors())
            .filter(c -> c.getParameterCount() == 1)
            .findFirst().orElseThrow(() -> new IllegalStateException("Constructor not found, no suitable constructor"));

        try {
            return (EntityId<?>) constructor.newInstance(
                deserializationContext.readTreeAsValue(node, constructor.getParameterTypes()[0])
            );
        } catch (Exception any) {
            throw new IllegalStateException(any.getMessage(), any);
        }
    }
}
