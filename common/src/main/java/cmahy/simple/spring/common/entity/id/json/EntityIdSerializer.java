package cmahy.simple.spring.common.entity.id.json;

import cmahy.simple.spring.common.entity.id.EntityId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class EntityIdSerializer extends StdSerializer<EntityId> {

    public EntityIdSerializer() {
        this(null);
    }

    public EntityIdSerializer(Class<EntityId> t) {
        super(t);
    }

    @Override
    public void serialize(EntityId tEntityId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        if (tEntityId != null && tEntityId.value() != null) {
            jsonGenerator.writeRawValue("\"" + tEntityId.value().toString() + "\"");
        }
    }
}
