package cmahy.webapp.resource.impl.application.taco.shop.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.resource.impl.application.mapper.PageableInputAppVoMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.ClientOrderPageOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.ClientOrderPagingRepository;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderPageOutputAppVo;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import jakarta.inject.Named;

@Query
@Named
public class GetAllClientOrderPagedQuery {

    private final ClientOrderPagingRepository clientOrderPagingRepository;
    private final ClientOrderPageOutputMapper clientOrderPageOutputMapper;
    private final PageableInputAppVoMapper pageableInputAppVoMapper;

    public GetAllClientOrderPagedQuery(
        ClientOrderPagingRepository clientOrderPagingRepository,
        ClientOrderPageOutputMapper clientOrderPageOutputMapper,
        PageableInputAppVoMapper pageableInputAppVoMapper
    ) {
        this.clientOrderPagingRepository = clientOrderPagingRepository;
        this.clientOrderPageOutputMapper = clientOrderPageOutputMapper;
        this.pageableInputAppVoMapper = pageableInputAppVoMapper;
    }

    public ClientOrderPageOutputAppVo execute(PageableInputAppVo pageable) {
        return clientOrderPageOutputMapper.map(
            clientOrderPagingRepository.findAll(
                pageableInputAppVoMapper.map(pageable)
            )
        );
    }
}
