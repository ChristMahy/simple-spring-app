package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.converter;

import cmahy.simple.spring.common.entity.id.EntityId;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.*;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.*;

@WritingConverter
public class TacoGenericIdWritingConverter implements GenericConverter {

    Set<ConvertiblePair> convertibles = Set.of(
        new ConvertiblePair(ClientOrderId.class, UUID.class),
        new ConvertiblePair(IngredientId.class, UUID.class),
        new ConvertiblePair(TacoId.class, UUID.class)
    );

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return convertibles;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (Objects.isNull(source)) {
            return null;
        }

        if (EntityId.class.isAssignableFrom(sourceType.getObjectType())) {
            return ((EntityId<UUID>) source).value();
        }

        throw new RuntimeException("Unsupported type: <" + sourceType.getObjectType().getName() + ">");
    }

}
