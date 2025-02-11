package cmahy.webapp.taco.shop.adapter.webclient.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.adapter.webclient.entity.domain.ExternalClientOrder;
import cmahy.webapp.taco.shop.kernel.application.repository.ClientOrderPagingRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.ClientOrderRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import cmahy.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.webapp.user.kernel.domain.User;
import jakarta.inject.Named;

@Named
@RemoteRepository
public class ExternalClientOrderRepositoryImpl implements ClientOrderRepository<ExternalClientOrder>, ClientOrderPagingRepository<ExternalClientOrder> {

    @Override
    public ClientOrderPage<ExternalClientOrder> getByUser(User user, EntityPageable pageable) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public ExternalClientOrder save(ExternalClientOrder clientOrder) {
        throw new IllegalStateException("Not yet implemented !");
    }
}
