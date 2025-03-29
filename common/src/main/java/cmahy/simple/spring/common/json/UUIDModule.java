package cmahy.simple.spring.common.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.module.*;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;

import java.io.Serial;
import java.util.UUID;

public final class UUIDModule extends SimpleModule {
    @Serial
    private static final long serialVersionUID = 1L;

    public UUIDModule() {
        super(
            "UUIDModule",
            new Version(
                1, 0, 0,
                "",
                "cmahy.simple.spring",
                "common"
            )
        );
    }

    @Override
    public void setupModule(SetupContext context) {

        SimpleSerializers serializers = new SimpleSerializers();
        SimpleDeserializers deserializers = new SimpleDeserializers();

        serializers.addSerializer(UUID.class, new UUIDSerializer());
        deserializers.addDeserializer(UUID.class, new UUIDDeserializer());

        context.addSerializers(serializers);
        context.addDeserializers(deserializers);

    }
}
