package cmahy.simple.spring.common.entity.id;

import com.fasterxml.jackson.annotation.*;

public interface EntityId<T> {

    @JsonValue
    T value();

}
