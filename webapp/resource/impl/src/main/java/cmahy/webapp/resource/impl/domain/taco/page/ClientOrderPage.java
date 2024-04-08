package cmahy.webapp.resource.impl.domain.taco.page;

import cmahy.common.entity.page.EntityPage;
import cmahy.webapp.resource.impl.domain.taco.ClientOrder;

import java.util.Collection;

public record ClientOrderPage(
    Collection<ClientOrder> content,
    Long totalElements
) implements EntityPage<ClientOrder> {
}
