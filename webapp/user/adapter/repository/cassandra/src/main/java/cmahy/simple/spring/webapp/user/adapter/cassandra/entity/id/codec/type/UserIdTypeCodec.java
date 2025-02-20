package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.codec.type;

import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;

import java.nio.ByteBuffer;

public class UserIdTypeCodec implements TypeCodec<UserId> {

    @Override
    public GenericType<UserId> getJavaType() {
        return GenericType.of(UserId.class);
    }

    @Override
    public DataType getCqlType() {
        return DataTypes.UUID;
    }

    @Override
    public ByteBuffer encode(UserId userId, ProtocolVersion protocolVersion) {
        if (userId == null) {
            return null;
        }

        return TypeCodecs.UUID.encode(userId.value(), protocolVersion);
    }

    @Override
    public UserId decode(ByteBuffer byteBuffer, ProtocolVersion protocolVersion) {
        if (byteBuffer == null) {
            return null;
        }

        return new UserId(TypeCodecs.UUID.decode(byteBuffer, protocolVersion));
    }

    @Override
    public String format(UserId userId) {
        if (userId == null) {
            return "";
        }

        return TypeCodecs.UUID.format(userId.value());
    }

    @Override
    public UserId parse(String value) {
        if (value == null) {
            return null;
        }

        return new UserId(TypeCodecs.UUID.parse(value));
    }
}
