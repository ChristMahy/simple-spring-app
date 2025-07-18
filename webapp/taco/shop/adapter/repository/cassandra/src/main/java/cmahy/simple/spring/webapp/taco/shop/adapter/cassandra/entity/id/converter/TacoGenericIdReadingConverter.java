package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.converter;

import cmahy.simple.spring.common.entity.id.EntityId;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.*;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.ReadingConverter;

import java.util.*;

@ReadingConverter
public class TacoGenericIdReadingConverter implements GenericConverter {

    Set<ConvertiblePair> convertibles = Set.of(
        new ConvertiblePair(UUID.class, ClientOrderId.class),
        new ConvertiblePair(UUID.class, IngredientId.class),
        new ConvertiblePair(UUID.class, TacoId .class)
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

        if (EntityId.class.isAssignableFrom(targetType.getObjectType())) {
            try {
                return targetType
                    .getObjectType()
                    .getDeclaredConstructor(UUID.class)
                    .newInstance((UUID) source);
            } catch (Exception e) {
                throw new RuntimeException("Instantiation error", e);
            }
        }

        throw new RuntimeException("Unsupported type: <" + targetType.getObjectType().getSimpleName() + ">");
    }

}
