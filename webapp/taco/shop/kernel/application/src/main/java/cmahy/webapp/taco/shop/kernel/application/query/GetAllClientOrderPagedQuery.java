package cmahy.webapp.taco.shop.kernel.application.query;

import cmahy.common.annotation.Query;
import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.ClientOrderPageOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.repository.ClientOrderPagingRepository;
import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.output.ClientOrderPageOutputVo;
import cmahy.webapp.user.kernel.application.repository.UserRepository;
import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.domain.id.UserId;
import cmahy.webapp.user.kernel.exception.UserNotFoundException;
import jakarta.inject.Named;

import java.util.Optional;

@Query
@Named
public class GetAllClientOrderPagedQuery {

    private final UserRepository<User> userRepository;
    private final ClientOrderPagingRepository<ClientOrder> clientOrderPagingRepository;
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
        Optional<User> user = userRepository.findById(userId);

        return clientOrderPageOutputMapper.map(
            clientOrderPagingRepository.getByUser(
                user.orElseThrow(() -> new UserNotFoundException(userId)),
                pageable
            )
        );
    }
}
