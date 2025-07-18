package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.codec.type;

import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;

import java.nio.ByteBuffer;
import java.util.Objects;

public class RightIdTypeCodec implements TypeCodec<RightId> {

    @Override
    public GenericType<RightId> getJavaType() {
        return GenericType.of(RightId.class);
    }

    @Override
    public DataType getCqlType() {
        return DataTypes.UUID;
    }

    @Override
    public ByteBuffer encode(RightId rightId, ProtocolVersion protocolVersion) {
        if (Objects.isNull(rightId)) {
            return null;
        }

        return TypeCodecs.UUID.encode(rightId.value(), protocolVersion);
    }

    @Override
    public RightId decode(ByteBuffer byteBuffer, ProtocolVersion protocolVersion) {
        if (Objects.isNull(byteBuffer)) {
            return null;
        }

        return new RightId(TypeCodecs.UUID.decode(byteBuffer, protocolVersion));
    }

    @Override
    public String format(RightId rightId) {
        if (Objects.isNull(rightId)) {
            return "";
        }

        return TypeCodecs.UUID.format(rightId.value());
    }

    @Override
    public RightId parse(String value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return new RightId(TypeCodecs.UUID.parse(value));
    }

}
