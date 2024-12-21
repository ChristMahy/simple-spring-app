package cmahy.webapp.taco.shop.kernel.domain.page;

import cmahy.common.entity.page.EntityPage;
import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;

import java.util.Collection;

public record ClientOrderPage<CO extends ClientOrder>(
    Collection<CO> content,
    Long totalElements
) implements EntityPage<CO> {
}
