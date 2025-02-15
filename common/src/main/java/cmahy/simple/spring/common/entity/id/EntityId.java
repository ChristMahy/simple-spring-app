package cmahy.simple.spring.common.entity.id;

import cmahy.simple.spring.common.entity.id.json.EntityIdDeserializer;
import cmahy.simple.spring.common.entity.id.json.EntityIdSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = EntityIdSerializer.class)
@JsonDeserialize(using = EntityIdDeserializer.class)
public interface EntityId<T> {

    @JsonProperty("id")
    T value();
}
