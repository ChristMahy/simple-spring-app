package cmahy.brokers.consumer.message.event.vo.id;

import cmahy.common.entity.id.EntityId;

public record MessageEventId(Long value) implements EntityId<Long> {
}
