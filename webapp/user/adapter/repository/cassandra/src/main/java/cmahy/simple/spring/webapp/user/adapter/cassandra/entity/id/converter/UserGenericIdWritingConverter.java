package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.converter;

import cmahy.simple.spring.common.entity.id.EntityId;
import cmahy.simple.spring.webapp.user.kernel.domain.id.*;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.*;

@WritingConverter
public class UserGenericIdWritingConverter implements GenericConverter {

    Set<ConvertiblePair> convertibleTypes = Set.of(
        new ConvertiblePair(RightId.class, UUID.class),
        new ConvertiblePair(RoleId.class, UUID.class),
        new ConvertiblePair(UserId.class, UUID.class)
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

        if (EntityId.class.isAssignableFrom(sourceType.getObjectType())) {
            return ((EntityId<UUID>) source).value();
        }

        throw new RuntimeException("Unsupported type: <" + sourceType.getObjectType().getName() + ">");
    }
    
}
