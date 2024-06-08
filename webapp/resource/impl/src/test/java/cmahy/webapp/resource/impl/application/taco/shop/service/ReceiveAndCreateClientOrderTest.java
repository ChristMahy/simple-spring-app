package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.input.ClientOrderInputAppMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.input.TacoInputAppMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.ClientOrderOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.*;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.application.user.repository.UserRepository;
import cmahy.webapp.resource.impl.domain.taco.*;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.domain.user.User;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.taco.IngredientNotFoundException;
import cmahy.webapp.resource.impl.exception.user.UserNotFoundException;
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
    private UserRepository userRepository;

    @Mock
    private ClientOrderRepository clientOrderRepository;

    @Mock
    private ClientOrderInputAppMapper clientOrderInputMapper;

    @Mock
    private TacoInputAppMapper tacoInputMapper;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private TacoRepository tacoRepository;

    @Mock
    private ClientOrderOutputMapper clientOrderOutputMapper;

    @InjectMocks
    private ReceiveAndCreateClientOrder receiveAndCreateClientOrder;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            UserId userId = new UserId(Generator.randomLongEqualOrAboveZero());
            User user = mock(User.class);
            ClientOrderInputAppVo clientOrderVo = new ClientOrderInputAppVo(
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Stream.generate(() -> {
                        TacoInputAppVo tacoInput = new TacoInputAppVo(
                            Generator.generateAString(),
                            Stream.generate(() -> {
                                    IngredientId id = new IngredientId(Generator.generateAString());

                                    when(ingredientRepository.findById(id.value())).thenAnswer((invocationOnMock) -> Optional.of(mock(Ingredient.class)));

                                    return id;
                                })
                                .limit(Generator.randomInt(10, 30))
                                .collect(Collectors.toSet())
                        );

                        Taco taco = mock(Taco.class);
                        when(tacoInputMapper.map(tacoInput)).thenAnswer((invocationOnMock) -> taco);
                        when(tacoRepository.save(taco)).thenAnswer((invocationOnMock) -> taco);

                        return tacoInput;
                    })
                    .limit(Generator.randomInt(5, 10))
                    .toList()
            );

            ClientOrder clientOrder = mock(ClientOrder.class);
            ClientOrderOutputAppVo clientOrderOutputVo = mock(ClientOrderOutputAppVo.class);

            when(userRepository.findById(userId.value())).thenReturn(Optional.of(user));
            when(clientOrderInputMapper.map(clientOrderVo, user)).thenReturn(clientOrder);
            when(clientOrderRepository.save(clientOrder)).thenReturn(clientOrder);
            when(clientOrderOutputMapper.map(clientOrder)).thenReturn(clientOrderOutputVo);

            ClientOrderOutputAppVo actual = receiveAndCreateClientOrder.execute(clientOrderVo, userId);

            assertThat(actual).isEqualTo(clientOrderOutputVo);
        });
    }

    @Test
    void execute_onIngredientNotFound_thenThrowNotFoundException() {
        IngredientId id = new IngredientId(Generator.generateAString());

        IngredientNotFoundException notFoundException = assertThrows(IngredientNotFoundException.class, () -> {
            UserId userId = new UserId(Generator.randomLongEqualOrAboveZero());
            User user = mock(User.class);
            ClientOrderInputAppVo clientOrderVo = new ClientOrderInputAppVo(
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Stream.generate(() -> {
                        TacoInputAppVo tacoInput = new TacoInputAppVo(
                            Generator.generateAString(),
                            Stream.generate(() -> {
                                    when(ingredientRepository.findById(id.value())).thenAnswer((invocationOnMock) -> Optional.empty());

                                    return id;
                                })
                                .limit(1)
                                .collect(Collectors.toSet())
                        );

                        Taco taco = mock(Taco.class);
                        when(tacoInputMapper.map(tacoInput)).thenAnswer((invocationOnMock) -> taco);

                        return tacoInput;
                    })
                    .limit(1)
                    .toList()
            );

            when(userRepository.findById(userId.value())).thenReturn(Optional.of(user));

            receiveAndCreateClientOrder.execute(clientOrderVo, userId);
        });

        assertThat(notFoundException).isNotNull();
        assertThat(notFoundException.getMessage()).contains(Ingredient.class.getSimpleName(), id.value());

        verify(tacoRepository, never()).save(any());
        verify(clientOrderRepository, never()).save(any());
    }

    @Test
    void execute_whenUserNotFound_thenThrowUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            UserId userId = new UserId(Generator.randomLongEqualOrAboveZero());
            ClientOrderInputAppVo clientOrderVo = mock(ClientOrderInputAppVo.class);

            when(userRepository.findById(userId.value())).thenReturn(Optional.empty());

            receiveAndCreateClientOrder.execute(clientOrderVo, userId);
        });
    }
}