package cmahy.simple.spring.common.entity.id;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface EntityId<T> {

    @JsonProperty("id")
    T value();
}
