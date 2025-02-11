package cmahy.webapp.taco.shop.adapter.cassandra.entity.id.codec.type;

import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;

import java.nio.ByteBuffer;

public class IngredentIdTypeCodec implements TypeCodec<IngredientId> {

    @Override
    public GenericType<IngredientId> getJavaType() {
        return GenericType.of(IngredientId.class);
    }

    @Override
    public DataType getCqlType() {
        return DataTypes.UUID;
    }

    @Override
    public ByteBuffer encode(IngredientId ingredientId, ProtocolVersion protocolVersion) {
        if (ingredientId == null) {
            return null;
        }

        return TypeCodecs.UUID.encode(ingredientId.value(), protocolVersion);
    }

    @Override
    public IngredientId decode(ByteBuffer byteBuffer, ProtocolVersion protocolVersion) {
        if (byteBuffer == null) {
            return null;
        }

        return new IngredientId(TypeCodecs.UUID.decode(byteBuffer, protocolVersion));
    }

    @Override
    public String format(IngredientId ingredientId) {
        if (ingredientId == null) {
            return "";
        }

        return TypeCodecs.UUID.format(ingredientId.value());
    }

    @Override
    public IngredientId parse(String value) {
        if (value == null) {
            return null;
        }

        return new IngredientId(TypeCodecs.UUID.parse(value));
    }
}
