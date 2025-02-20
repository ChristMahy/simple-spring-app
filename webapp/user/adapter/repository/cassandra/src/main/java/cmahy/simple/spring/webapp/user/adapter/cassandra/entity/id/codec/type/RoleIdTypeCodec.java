package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.codec.type;

import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;

import java.nio.ByteBuffer;

public class RoleIdTypeCodec implements TypeCodec<RoleId> {

    @Override
    public GenericType<RoleId> getJavaType() {
        return GenericType.of(RoleId.class);
    }

    @Override
    public DataType getCqlType() {
        return DataTypes.UUID;
    }

    @Override
    public ByteBuffer encode(RoleId roleId, ProtocolVersion protocolVersion) {
        if (roleId == null) {
            return null;
        }

        return TypeCodecs.UUID.encode(roleId.value(), protocolVersion);
    }

    @Override
    public RoleId decode(ByteBuffer byteBuffer, ProtocolVersion protocolVersion) {
        if (byteBuffer == null) {
            return null;
        }

        return new RoleId(TypeCodecs.UUID.decode(byteBuffer, protocolVersion));
    }

    @Override
    public String format(RoleId roleId) {
        if (roleId == null) {
            return "";
        }

        return TypeCodecs.UUID.format(roleId.value());
    }

    @Override
    public RoleId parse(String value) {
        if (value == null) {
            return null;
        }

        return new RoleId(TypeCodecs.UUID.parse(value));
    }
}
