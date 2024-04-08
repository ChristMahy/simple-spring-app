package cmahy.webapp.resource.impl.application.taco.shop.vo.output;

import cmahy.common.entity.page.EntityPage;

import java.util.Collection;

public record ClientOrderPageOutputAppVo(
    Collection<ClientOrderOutputAppVo> content,
    Long totalElements
) implements EntityPage<ClientOrderOutputAppVo> {
}
