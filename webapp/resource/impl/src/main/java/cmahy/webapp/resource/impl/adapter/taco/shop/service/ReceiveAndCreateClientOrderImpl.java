package cmahy.webapp.resource.impl.adapter.taco.shop.service;

import cmahy.webapp.resource.impl.application.taco.shop.mapper.input.ClientOrderInputAppMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.input.TacoInputAppMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.ClientOrderOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.*;
import cmahy.webapp.resource.impl.application.taco.shop.service.ReceiveAndCreateClientOrder;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.application.user.repository.UserRepository;
import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.domain.user.User;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.taco.IngredientNotFoundException;
import cmahy.webapp.resource.impl.exception.user.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReceiveAndCreateClientOrderImpl implements ReceiveAndCreateClientOrder {

    private final UserRepository userRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final ClientOrderInputAppMapper clientOrderInputMapper;
    private final TacoInputAppMapper tacoInputMapper;
    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private final ClientOrderOutputMapper clientOrderOutputMapper;

    public ReceiveAndCreateClientOrderImpl(
        UserRepository userRepository,
        ClientOrderRepository clientOrderRepository,
        ClientOrderInputAppMapper clientOrderInputMapper,
        TacoInputAppMapper tacoInputMapper,
        IngredientRepository ingredientRepository,
        TacoRepository tacoRepository,
        ClientOrderOutputMapper clientOrderOutputMapper
    ) {
        this.userRepository = userRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.clientOrderInputMapper = clientOrderInputMapper;
        this.tacoInputMapper = tacoInputMapper;
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.clientOrderOutputMapper = clientOrderOutputMapper;
    }

    @Override
    @Transactional
    public ClientOrderOutputAppVo execute(ClientOrderInputAppVo input, UserId clientId) {
        User user = userRepository.findById(clientId.value())
            .orElseThrow(() -> new UserNotFoundException(clientId));

        ClientOrder clientOrder = clientOrderInputMapper.map(input, user);

        clientOrder.setTacos(input.tacos().stream().map(tacoInput -> {
            var taco = tacoInputMapper.map(tacoInput);

            taco.setIngredients(
                tacoInput.ingredientIds().stream()
                    .map(ingredientId -> ingredientRepository
                        .findById(ingredientId.value())
                        .orElseThrow(() -> new IngredientNotFoundException(new IngredientId(ingredientId.value())))
                    )
                    .distinct()
                    .toList()
            );

            return tacoRepository.save(taco);
        }).toList());

        return clientOrderOutputMapper.map(
            this.clientOrderRepository.save(
                clientOrder
            )
        );
    }
}
