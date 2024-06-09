package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.webapp.resource.impl.application.taco.shop.mapper.input.ClientOrderInputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.input.TacoInputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.ClientOrderOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.*;
import cmahy.webapp.resource.impl.application.user.repository.UserRepository;
import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import cmahy.webapp.resource.impl.domain.user.User;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.taco.IngredientNotFoundException;
import cmahy.webapp.resource.impl.exception.user.UserNotFoundException;
import cmahy.webapp.resource.taco.shop.id.IngredientId;
import cmahy.webapp.resource.taco.shop.vo.input.ClientOrderInputVo;
import cmahy.webapp.resource.taco.shop.vo.output.ClientOrderOutputVo;
import jakarta.inject.Named;

@Named
public class ReceiveAndCreateClientOrder {

    private final UserRepository userRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final ClientOrderInputMapper clientOrderInputMapper;
    private final TacoInputMapper tacoInputMapper;
    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private final ClientOrderOutputMapper clientOrderOutputMapper;

    public ReceiveAndCreateClientOrder(
        UserRepository userRepository,
        ClientOrderRepository clientOrderRepository,
        ClientOrderInputMapper clientOrderInputMapper,
        TacoInputMapper tacoInputMapper,
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

    public ClientOrderOutputVo execute(ClientOrderInputVo input, UserId clientId) {
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
