package cmahy.webapp.resource.impl.adapter.taco.shop.service;

import cmahy.webapp.resource.impl.application.taco.shop.mapper.input.ClientOrderInputAppMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.input.TacoInputAppMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.ClientOrderOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.*;
import cmahy.webapp.resource.impl.application.taco.shop.service.ReceiveAndCreateClientOrder;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.taco.IngredientNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReceiveAndCreateClientOrderImpl implements ReceiveAndCreateClientOrder {

    private final ClientOrderRepository clientOrderRepository;
    private final ClientOrderInputAppMapper clientOrderInputMapper;
    private final TacoInputAppMapper tacoInputMapper;
    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private final ClientOrderOutputMapper clientOrderOutputMapper;

    public ReceiveAndCreateClientOrderImpl(
        ClientOrderRepository clientOrderRepository,
        ClientOrderInputAppMapper clientOrderInputMapper,
        TacoInputAppMapper tacoInputMapper,
        IngredientRepository ingredientRepository,
        TacoRepository tacoRepository,
        ClientOrderOutputMapper clientOrderOutputMapper
    ) {
        this.clientOrderRepository = clientOrderRepository;
        this.clientOrderInputMapper = clientOrderInputMapper;
        this.tacoInputMapper = tacoInputMapper;
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.clientOrderOutputMapper = clientOrderOutputMapper;
    }

    @Override
    @Transactional
    public ClientOrderOutputAppVo execute(ClientOrderInputAppVo input) {
        var clientOrder = clientOrderInputMapper.map(input);

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
