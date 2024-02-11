package cmahy.brokers.consumer.message.event.vo.id;

import cmahy.common.entity.EntityId;

public record MessageEventId(Long value) implements EntityId<Long> {
}
