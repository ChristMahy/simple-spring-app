package cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output;

import cmahy.simple.spring.common.entity.id.EntityId;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output.ClientOrderOutputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output.TacoOutputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.ClientOrderOutputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.TacoOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientOrderOutputMapperTest {

    @Mock
    private TacoOutputMapper tacoOutputMapper;

    @InjectMocks
    private ClientOrderOutputMapper clientOrderOutputMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            List<TacoStub> tacos = Stream.generate(() -> mock(TacoStub.class))
                .limit(randomInt(10, 100))
                .toList();
            List<TacoOutputVo> tacoOutputVos = tacos.stream()
                .map(taco -> {
                    TacoOutputVo outputVo = mock(TacoOutputVo.class);

                    try {
                        when(tacoOutputMapper.map(taco)).thenReturn(outputVo);
                    } catch (RequiredException ignored) {}

                    return outputVo;
                })
                .toList();

            ClientOrder order = new ClientOrderStub()
                .setId(randomUUID())
                .setPlacedAt(new Date())
                .setDeliveryName(generateAString())
                .setDeliveryStreet(generateAString())
                .setDeliveryState(generateAString())
                .setDeliveryCity(generateAString())
                .setDeliveryZip(generateAString())
                .setCcNumber(generateAString())
                .setCcCVV(generateAString())
                .setCcExpiration(generateAString())
                .setTacos(tacos);

            ClientOrderOutputVo actual = clientOrderOutputMapper.map(order);

            BiPredicate<EntityId<?>, ?> idPredicate = (entityId, id) -> (Objects.isNull(entityId) && Objects.isNull(id)) || (Objects.nonNull(entityId) && entityId.value().equals(id));

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("tacos")
                .withEqualsForFields(idPredicate, "id")
                .isEqualTo(order);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(RequiredException.class, () -> {
            clientOrderOutputMapper.map(null);
        });
    }
}