package cmahy.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface EntityId<T> {

    @JsonProperty("id")
    T getValue();
}
