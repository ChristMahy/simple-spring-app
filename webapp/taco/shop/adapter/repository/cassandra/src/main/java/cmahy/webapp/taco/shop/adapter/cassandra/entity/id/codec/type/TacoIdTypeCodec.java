package cmahy.webapp.taco.shop.adapter.cassandra.entity.id.codec.type;

import cmahy.webapp.taco.shop.kernel.domain.id.TacoId;
import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;

import java.nio.ByteBuffer;

public class TacoIdTypeCodec implements TypeCodec<TacoId> {

    @Override
    public GenericType<TacoId> getJavaType() {
        return GenericType.of(TacoId.class);
    }

    @Override
    public DataType getCqlType() {
        return DataTypes.UUID;
    }

    @Override
    public ByteBuffer encode(TacoId tacoId, ProtocolVersion protocolVersion) {
        if (tacoId == null) {
            return null;
        }

        return TypeCodecs.UUID.encode(tacoId.value(), protocolVersion);
    }

    @Override
    public TacoId decode(ByteBuffer byteBuffer, ProtocolVersion protocolVersion) {
        if (byteBuffer == null) {
            return null;
        }

        return new TacoId(TypeCodecs.UUID.decode(byteBuffer, protocolVersion));
    }

    @Override
    public String format(TacoId tacoId) {
        if (tacoId == null) {
            return "";
        }

        return TypeCodecs.UUID.format(tacoId.value());
    }

    @Override
    public TacoId parse(String value) {
        if (value == null) {
            return null;
        }

        return new TacoId(TypeCodecs.UUID.parse(value));
    }
}
