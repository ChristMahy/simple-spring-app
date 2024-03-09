package cmahy.webapp.resource.impl.adapter.taco.shop.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.input.ClientOrderInputAppMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.input.TacoInputAppMapper;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.ClientOrderOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.*;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.*;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.taco.IngredientNotFoundException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiveAndCreateClientOrderImplTest {

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
    private ReceiveAndCreateClientOrderImpl receiveAndCreateClientOrderImpl;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
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

            when(clientOrderInputMapper.map(clientOrderVo)).thenReturn(clientOrder);
            when(clientOrderRepository.save(clientOrder)).thenReturn(clientOrder);
            when(clientOrderOutputMapper.map(clientOrder)).thenReturn(clientOrderOutputVo);

            ClientOrderOutputAppVo actual = receiveAndCreateClientOrderImpl.execute(clientOrderVo);

            assertThat(actual).isEqualTo(clientOrderOutputVo);
        });
    }

    @Test
    void execute_onIngredientNotFound_thenThrowNotFoundException() {
        IngredientId id = new IngredientId(Generator.generateAString());

        IngredientNotFoundException notFoundException = assertThrows(IngredientNotFoundException.class, () -> {
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

            receiveAndCreateClientOrderImpl.execute(clientOrderVo);
        });

        assertThat(notFoundException).isNotNull();
        assertThat(notFoundException.getMessage()).contains(Ingredient.class.getName(), id.value());

        verify(tacoRepository, never()).save(any());
        verify(clientOrderRepository, never()).save(any());
    }
}