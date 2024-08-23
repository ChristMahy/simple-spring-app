package cmahy.webapp.taco.shop.kernel.application.service;

import cmahy.webapp.taco.shop.kernel.application.mapper.input.ClientOrderInputMapper;
import cmahy.webapp.taco.shop.kernel.application.mapper.input.TacoInputMapper;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.ClientOrderOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.repository.*;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientNotFoundException;
import cmahy.webapp.taco.shop.kernel.domain.*;
import cmahy.webapp.taco.shop.kernel.vo.input.ClientOrderInputVo;
import cmahy.webapp.taco.shop.kernel.vo.input.TacoInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.ClientOrderOutputVo;
import cmahy.webapp.user.kernel.application.repository.UserRepository;
import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.domain.id.UserId;
import cmahy.webapp.user.kernel.exception.UserNotFoundException;
import jakarta.inject.Named;

import java.util.*;

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

            taco.setIngredients(ingredients.stream().distinct().toList());

            tacos.add(tacoRepository.save(taco));
        }

        clientOrder.setTacos(Collections.unmodifiableList(tacos));

        return clientOrderOutputMapper.map(
            this.clientOrderRepository.save(
                clientOrder
            )
        );
    }
}
