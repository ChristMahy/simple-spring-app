package cmahy.webapp.taco.shop.kernel.application.query;

import cmahy.common.annotation.Query;
import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.ClientOrderPageOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.repository.ClientOrderPagingRepository;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.output.ClientOrderPageOutputVo;
import cmahy.webapp.user.kernel.application.repository.UserRepository;
import cmahy.webapp.user.kernel.domain.id.UserId;
import cmahy.webapp.user.kernel.exception.UserNotFoundException;
import jakarta.inject.Named;

@Query
@Named
public class GetAllClientOrderPagedQuery {

    private final UserRepository userRepository;
    private final ClientOrderPagingRepository clientOrderPagingRepository;
    private final ClientOrderPageOutputMapper clientOrderPageOutputMapper;

    public GetAllClientOrderPagedQuery(
        UserRepository userRepository,
        ClientOrderPagingRepository clientOrderPagingRepository,
        ClientOrderPageOutputMapper clientOrderPageOutputMapper
    ) {
        this.userRepository = userRepository;
        this.clientOrderPagingRepository = clientOrderPagingRepository;
        this.clientOrderPageOutputMapper = clientOrderPageOutputMapper;
    }

    public ClientOrderPageOutputVo execute(UserId userId, EntityPageable pageable) throws UserNotFoundException, RequiredException {
        return clientOrderPageOutputMapper.map(
            clientOrderPagingRepository.getByUser(
                userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)),
                pageable
            )
        );
    }
}
