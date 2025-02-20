package cmahy.simple.spring.webapp.taco.shop.kernel.application.service;

import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.input.ClientOrderInputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.input.TacoInputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output.ClientOrderOutputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.ingredient.IngredientNotFoundException;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.ClientOrderInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.TacoInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.ClientOrderOutputVo;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import cmahy.simple.spring.webapp.user.kernel.exception.UserNotFoundException;
import jakarta.inject.Named;

import java.util.*;

@Named
public class ReceiveAndCreateClientOrder {

    private final UserRepository<User> userRepository;
    private final ClientOrderRepository<ClientOrder> clientOrderRepository;
    private final ClientOrderInputMapper clientOrderInputMapper;
    private final TacoInputMapper tacoInputMapper;
    private final IngredientRepository<Ingredient> ingredientRepository;
    private final TacoRepository<Taco> tacoRepository;
    private final ClientOrderOutputMapper clientOrderOutputMapper;
    private final TacoBuilderFactory<Taco> tacoBuilderFactory;
    private final ClientOrderBuilderFactory<ClientOrder> clientOrderBuilderFactory;

    public ReceiveAndCreateClientOrder(
        UserRepository userRepository,
        ClientOrderRepository clientOrderRepository,
        ClientOrderInputMapper clientOrderInputMapper,
        TacoInputMapper tacoInputMapper,
        IngredientRepository ingredientRepository,
        TacoRepository tacoRepository,
        ClientOrderOutputMapper clientOrderOutputMapper,
        TacoBuilderFactory tacoBuilderFactory,
        ClientOrderBuilderFactory clientOrderBuilderFactory
    ) {
        this.userRepository = userRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.clientOrderInputMapper = clientOrderInputMapper;
        this.tacoInputMapper = tacoInputMapper;
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.clientOrderOutputMapper = clientOrderOutputMapper;
        this.tacoBuilderFactory = tacoBuilderFactory;
        this.clientOrderBuilderFactory = clientOrderBuilderFactory;
    }

    public ClientOrderOutputVo execute(ClientOrderInputVo input, UserId clientId) throws RequiredException, UserNotFoundException, IngredientNotFoundException {
        User user = userRepository.findById(clientId)
            .orElseThrow(() -> new UserNotFoundException(clientId));

        ClientOrder clientOrder = clientOrderInputMapper.map(input, user);

        ArrayList<Taco> tacos = new ArrayList<>(input.tacos().size());

        for (TacoInputVo tacoInputVo : input.tacos()) {
            Taco taco = tacoInputMapper.map(tacoInputVo);

            List<Ingredient> ingredients = new ArrayList<>(taco.getIngredients().size());

            for (IngredientId ingredientId : tacoInputVo.ingredientIds()) {
                ingredients.add(
                    ingredientRepository
                        .findById(ingredientId)
                        .orElseThrow(() -> new IngredientNotFoundException(new IngredientId(ingredientId.value())))
                );
            }

            taco = tacoBuilderFactory.create(taco)
                .ingredients(ingredients.stream().distinct().toList())
                .build();

            tacos.add(tacoRepository.save(taco));
        }

        clientOrder = clientOrderBuilderFactory.create(clientOrder)
            .tacos(Collections.unmodifiableList(tacos))
            .build();

        return clientOrderOutputMapper.map(
            this.clientOrderRepository.save(
                clientOrder
            )
        );
    }
}
