package cmahy.simple.spring.brokers.publisher.event.vo.id;

import cmahy.simple.spring.common.entity.id.EntityId;

public record MessageEventId(Long value) implements EntityId<Long> {
}
