package cmahy.common.entity.json;

import cmahy.common.entity.EntityId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class EntityIdSerializer extends StdSerializer<EntityId<?>> {

    public EntityIdSerializer() {
        this(null);
    }

    public EntityIdSerializer(Class<EntityId<?>> t) {
        super(t);
    }

    @Override
    public void serialize(EntityId<?> tEntityId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        if (tEntityId != null && tEntityId.value() != null) {
            jsonGenerator.writeRawValue(tEntityId.value().toString());
        }
    }
}
