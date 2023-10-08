package cmahy.common.entity;

import cmahy.common.entity.json.EntityIdDeserializer;
import cmahy.common.entity.json.EntityIdSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = EntityIdSerializer.class)
@JsonDeserialize(using = EntityIdDeserializer.class)
public interface EntityId<T> {

    @JsonProperty("id")
    T value();
}
