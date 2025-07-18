package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.impl;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraIngredientProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraIngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.IngredientPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Slice;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientRepositoryImplTest {

    @Mock
    private CassandraIngredientRepository ingredientRepository;

    @Mock
    private CassandraTacoProxyFactoryProvider factoryProvider;

    @InjectMocks
    private IngredientRepositoryImpl ingredientRepositoryImpl;

    @Mock
    private CassandraIngredientProxyFactory ingredientProxyFactory;

    @BeforeEach
    void setUp() {
        lenient().when(factoryProvider.resolve(CassandraIngredient.class)).thenReturn(ingredientProxyFactory);
    }

    @Test
    void findAll() {
        assertDoesNotThrow(() -> {

            Long elementsSize = Generator.randomLongEqualOrAboveZero();

            EntityPageable pageable = mock(EntityPageable.class);
            Slice<CassandraIngredient> sliceResult = mock(Slice.class);

            List<CassandraIngredient> ingredients = mock(List.class);
            List<CassandraIngredientProxy> ingredientProxies = new ArrayList<>();

            Stream<CassandraIngredient> stream = Stream
                .generate(() -> {
                    CassandraIngredient ingredient = mock(CassandraIngredient.class);
                    CassandraIngredientProxy ingredientProxy = mock(CassandraIngredientProxy.class);

                    when(ingredientProxyFactory.create(ingredient)).thenReturn(ingredientProxy);
                    ingredientProxies.add(ingredientProxy);

                    return ingredient;
                })
                .limit(Generator.randomInt(100, 1000));

            when(ingredients.stream()).thenReturn(stream);

            when(sliceResult.getContent()).thenReturn(ingredients);

            when(ingredientRepository.findAll(pageable)).thenReturn(sliceResult);
            when(ingredientRepository.count()).thenReturn(elementsSize);

            IngredientPage<CassandraIngredientProxy> actual = ingredientRepositoryImpl.findAll(pageable);

            assertThat(actual).isNotNull();

            assertThat(actual.content()).containsExactlyInAnyOrderElementsOf(ingredientProxies);
            assertThat(actual.totalElements()).isEqualTo(elementsSize);
        });
    }

    @Test
    void findById() {
        assertDoesNotThrow(() -> {

            IngredientId ingredientId = mock(IngredientId.class);
            CassandraIngredient ingredient = mock(CassandraIngredient.class);
            CassandraIngredientProxy ingredientProxy = mock(CassandraIngredientProxy.class);

            when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
            when(ingredientProxyFactory.create(ingredient)).thenReturn(ingredientProxy);

            Optional<CassandraIngredientProxy> actual = ingredientRepositoryImpl.findById(ingredientId);

            assertThat(actual)
                .isNotEmpty()
                .hasValue(ingredientProxy);
        });
    }

    @Test
    void findByType() {
        assertDoesNotThrow(() -> {

            IngredientType type = mock(IngredientType.class);

            Set<CassandraIngredient> ingredients = mock(Set.class);
            Set<CassandraIngredientProxy> ingredientProxies = new HashSet<>();

            Stream<CassandraIngredient> stream = Stream
                .generate(() -> {
                    CassandraIngredient ingredient = mock(CassandraIngredient.class);
                    CassandraIngredientProxy ingredientProxy = mock(CassandraIngredientProxy.class);

                    when(ingredientProxyFactory.create(ingredient)).thenReturn(ingredientProxy);
                    ingredientProxies.add(ingredientProxy);

                    return ingredient;
                })
                .limit(Generator.randomInt(100, 1000));

            when(ingredients.stream()).thenReturn(stream);

            when(ingredientRepository.findByType(type)).thenReturn(ingredients);

            Set<CassandraIngredientProxy> actual = ingredientRepositoryImpl.findByType(type);

            assertThat(actual).isNotNull();

            assertThat(actual).containsExactlyInAnyOrderElementsOf(ingredientProxies);
        });
    }

    @Test
    void findByNameAndType() {
        assertDoesNotThrow(() -> {

            String name = Generator.generateAString();
            IngredientType type = mock(IngredientType.class);

            CassandraIngredient ingredient = mock(CassandraIngredient.class);
            CassandraIngredientProxy ingredientProxy = mock(CassandraIngredientProxy.class);

            when(ingredientRepository.findByNameAndType(name, type)).thenReturn(Optional.of(ingredient));
            when(ingredientProxyFactory.create(ingredient)).thenReturn(ingredientProxy);

            Optional<CassandraIngredientProxy> actual = ingredientRepositoryImpl.findByNameAndType(name, type);

            assertThat(actual)
                .isNotEmpty()
                .hasValue(ingredientProxy);
        });
    }

    @Test
    void save() {
        assertDoesNotThrow(() -> {

            UUID ingredientId = mock(UUID.class);
            CassandraIngredient inIngredient = mock(CassandraIngredient.class);
            when(inIngredient.getId()).thenReturn(ingredientId);

            CassandraIngredientProxy inIngredientProxy = mock(CassandraIngredientProxy.class);

            when(inIngredientProxy.unwrap()).thenReturn(inIngredient);

            CassandraIngredient outIngredient = mock(CassandraIngredient.class);
            CassandraIngredientProxy outIngredientProxy = mock(CassandraIngredientProxy.class);

            when(ingredientRepository.save(inIngredient)).thenReturn(outIngredient);
            when(ingredientProxyFactory.create(outIngredient)).thenReturn(outIngredientProxy);

            CassandraIngredientProxy actual = ingredientRepositoryImpl.save(inIngredientProxy);

            assertThat(actual)
                .isNotNull()
                .isSameAs(outIngredientProxy);

            verify(inIngredient, never()).setId(any(UUID.class));
        });
    }

    @Test
    void save_whenIsIsNull_thenGenerateNewOne() {
        assertDoesNotThrow(() -> {

            try (MockedStatic<UUID> uuidMock = Mockito.mockStatic(UUID.class)) {
                UUID ingredientId = mock(UUID.class);

                uuidMock.when(() -> UUID.randomUUID()).thenReturn(ingredientId);

                CassandraIngredient inIngredient = mock(CassandraIngredient.class);
                CassandraIngredientProxy inIngredientProxy = mock(CassandraIngredientProxy.class);

                when(inIngredientProxy.unwrap()).thenReturn(inIngredient);

                CassandraIngredient outIngredient = mock(CassandraIngredient.class);
                CassandraIngredientProxy outIngredientProxy = mock(CassandraIngredientProxy.class);

                when(ingredientRepository.save(inIngredient)).thenReturn(outIngredient);
                when(ingredientProxyFactory.create(outIngredient)).thenReturn(outIngredientProxy);

                CassandraIngredientProxy actual = ingredientRepositoryImpl.save(inIngredientProxy);

                assertThat(actual)
                    .isNotNull()
                    .isSameAs(outIngredientProxy);

                verify(inIngredient).setId(any(UUID.class));
            }
        });
    }

    @Test
    void deleteById() {
        assertDoesNotThrow(() -> {

            IngredientId ingredientId = mock(IngredientId.class);

            ingredientRepositoryImpl.deleteById(ingredientId);

            verify(ingredientRepository).deleteById(ingredientId);

            verifyNoMoreInteractions(ingredientRepository);
            verifyNoInteractions(ingredientProxyFactory);
        });
    }

    @Test
    void deleteById_whenGivenIdIsNull_thenExecute() {
        assertDoesNotThrow(() -> {
            ingredientRepositoryImpl.deleteById(null);

            verify(ingredientRepository).deleteById((IngredientId) null);
        });
    }
}