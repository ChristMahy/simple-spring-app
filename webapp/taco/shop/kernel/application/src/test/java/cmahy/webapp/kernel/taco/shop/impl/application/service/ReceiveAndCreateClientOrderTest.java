package cmahy.webapp.kernel.taco.shop.impl.application.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.application.mapper.input.ClientOrderInputMapper;
import cmahy.webapp.taco.shop.kernel.application.mapper.input.TacoInputMapper;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.ClientOrderOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.repository.*;
import cmahy.webapp.taco.shop.kernel.application.service.ReceiveAndCreateClientOrder;
import cmahy.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilder;
import cmahy.webapp.taco.shop.kernel.domain.builder.TacoBuilder;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactory;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactory;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiveAndCreateClientOrderTest {

    @Mock
    private UserRepository<User> userRepository;

    @Mock
    private ClientOrderRepository<ClientOrder> clientOrderRepository;

    @Mock
    private ClientOrderInputMapper clientOrderInputMapper;

    @Mock
    private TacoInputMapper tacoInputMapper;

    @Mock
    private IngredientRepository<Ingredient> ingredientRepository;

    @Mock
    private TacoRepository<Taco> tacoRepository;

    @Mock
    private ClientOrderOutputMapper clientOrderOutputMapper;

    @Mock
    private TacoBuilderFactory<Taco> tacoBuilderFactory;

    @Mock
    private ClientOrderBuilderFactory<ClientOrder> clientOrderBuilderFactory;

    @InjectMocks
    private ReceiveAndCreateClientOrder receiveAndCreateClientOrder;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            UserId userId = new UserId(Generator.randomUUID());
            User user = mock(User.class);
            ClientOrderInputVo clientOrderVo = new ClientOrderInputVo(
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Stream.generate(() -> {
                        TacoInputVo tacoInput = new TacoInputVo(
                            Generator.generateAString(),
                            Stream.generate(() -> {
                                    IngredientId id = new IngredientId(Generator.randomUUID());

                                    when(ingredientRepository.findById(id)).thenAnswer(_ -> Optional.of(mock(Ingredient.class)));

                                    return id;
                                })
                                .limit(Generator.randomInt(10, 30))
                                .collect(Collectors.toSet())
                        );

                        Taco taco = mock(Taco.class);
                        TacoBuilder<Taco> tacoBuilder = mock(TacoBuilder.class, RETURNS_SELF);
                        try {
                            when(tacoInputMapper.map(tacoInput)).thenAnswer(_ -> taco);
                        } catch (RequiredException ignored) {}
                        when(tacoBuilderFactory.create(taco)).thenReturn(tacoBuilder);
                        when(tacoBuilder.build()).thenReturn(taco);
                        when(tacoRepository.save(taco)).thenAnswer(_ -> taco);

                        return tacoInput;
                    })
                    .limit(Generator.randomInt(5, 10))
                    .toList()
            );

            ClientOrder clientOrder = mock(ClientOrder.class);
            ClientOrderOutputVo clientOrderOutputVo = mock(ClientOrderOutputVo.class);
            ClientOrderBuilder<ClientOrder> clientOrderBuilder = mock(ClientOrderBuilder.class, RETURNS_SELF);

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(clientOrderInputMapper.map(clientOrderVo, user)).thenReturn(clientOrder);
            when(clientOrderBuilderFactory.create(clientOrder)).thenReturn(clientOrderBuilder);
            when(clientOrderBuilder.build()).thenReturn(clientOrder);
            when(clientOrderRepository.save(clientOrder)).thenReturn(clientOrder);
            when(clientOrderOutputMapper.map(clientOrder)).thenReturn(clientOrderOutputVo);

            ClientOrderOutputVo actual = receiveAndCreateClientOrder.execute(clientOrderVo, userId);

            assertThat(actual).isEqualTo(clientOrderOutputVo);
        });
    }

    @Test
    void execute_onIngredientNotFound_thenThrowNotFoundException() {
        IngredientId id = new IngredientId(Generator.randomUUID());

        IngredientNotFoundException notFoundException = assertThrows(IngredientNotFoundException.class, () -> {
            UserId userId = new UserId(Generator.randomUUID());
            User user = mock(User.class);
            ClientOrderInputVo clientOrderVo = new ClientOrderInputVo(
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Stream.generate(() -> {
                        TacoInputVo tacoInput = new TacoInputVo(
                            Generator.generateAString(),
                            Stream.generate(() -> {
                                    when(ingredientRepository.findById(id)).thenAnswer(_ -> Optional.empty());

                                    return id;
                                })
                                .limit(1)
                                .collect(Collectors.toSet())
                        );

                        Taco taco = mock(Taco.class);
                        try {
                            when(tacoInputMapper.map(tacoInput)).thenAnswer(_ -> taco);
                        } catch (RequiredException ignored) {}

                        return tacoInput;
                    })
                    .limit(1)
                    .toList()
            );

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));

            receiveAndCreateClientOrder.execute(clientOrderVo, userId);
        });

        assertThat(notFoundException).isNotNull();
        assertThat(notFoundException.getMessage()).contains(Ingredient.class.getSimpleName(), id.value().toString());

        verify(tacoRepository, never()).save(any());
        verify(clientOrderRepository, never()).save(any());
    }

    @Test
    void execute_whenUserNotFound_thenThrowUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            UserId userId = new UserId(Generator.randomUUID());
            ClientOrderInputVo clientOrderVo = mock(ClientOrderInputVo.class);

            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            receiveAndCreateClientOrder.execute(clientOrderVo, userId);
        });
    }
}