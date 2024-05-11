package cmahy.webapp.resource.impl.application.taco.shop.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.resource.impl.application.mapper.PageableInputAppVoMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.ClientOrderPageOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.ClientOrderPagingRepository;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderPageOutputAppVo;
import cmahy.webapp.resource.impl.application.user.repository.UserRepository;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.user.UserNotFoundException;
import jakarta.inject.Named;

@Query
@Named
public class GetAllClientOrderPagedQuery {

    private final UserRepository userRepository;
    private final ClientOrderPagingRepository clientOrderPagingRepository;
    private final ClientOrderPageOutputMapper clientOrderPageOutputMapper;
    private final PageableInputAppVoMapper pageableInputAppVoMapper;

    public GetAllClientOrderPagedQuery(
        UserRepository userRepository,
        ClientOrderPagingRepository clientOrderPagingRepository,
        ClientOrderPageOutputMapper clientOrderPageOutputMapper,
        PageableInputAppVoMapper pageableInputAppVoMapper
    ) {
        this.userRepository = userRepository;
        this.clientOrderPagingRepository = clientOrderPagingRepository;
        this.clientOrderPageOutputMapper = clientOrderPageOutputMapper;
        this.pageableInputAppVoMapper = pageableInputAppVoMapper;
    }

    public ClientOrderPageOutputAppVo execute(UserId userId, PageableInputAppVo pageable) {
        return clientOrderPageOutputMapper.map(
            clientOrderPagingRepository.getByUser(
                userRepository.findById(userId.value()).orElseThrow(() -> new UserNotFoundException(userId)),
                pageableInputAppVoMapper.map(pageable)
            )
        );
    }
}
