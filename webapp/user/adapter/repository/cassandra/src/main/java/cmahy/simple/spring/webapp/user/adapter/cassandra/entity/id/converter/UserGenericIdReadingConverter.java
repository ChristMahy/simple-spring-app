package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.converter;

import cmahy.simple.spring.common.entity.id.EntityId;
import cmahy.simple.spring.webapp.user.kernel.domain.id.*;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.ReadingConverter;

import java.util.*;

@ReadingConverter
public class UserGenericIdReadingConverter implements GenericConverter {

    Set<ConvertiblePair> convertibleTypes = Set.of(
        new ConvertiblePair(UUID.class, RightId.class),
        new ConvertiblePair(UUID.class, RoleId.class),
        new ConvertiblePair(UUID.class, UserId.class)
    );

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return convertibleTypes;
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
