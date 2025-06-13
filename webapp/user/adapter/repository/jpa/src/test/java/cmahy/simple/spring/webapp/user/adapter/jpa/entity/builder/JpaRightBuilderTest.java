package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRight;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRole;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class JpaRightBuilderTest {

    @Test
    void build() {
        assertDoesNotThrow(() -> {

            String name = Generator.generateAString();
            List<JpaRole> roles = Stream
                .generate(() -> mock(JpaRole.class))
                .limit(Generator.randomInt(50, 500))
                .toList();


            Right actual = new JpaRightBuilder()
                .name(name)
                .roles(roles)
                .build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaRight.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getRoles()).containsExactlyElementsOf(roles);

        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {

            String name = Generator.generateAString();
            List<JpaRole> roles = Stream
                .generate(() -> mock(JpaRole.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            JpaRight originalRight = new JpaRight()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString())
                .setRoles(
                    Stream
                        .generate(() -> mock(JpaRole.class))
                        .limit(40)
                        .toList()
                );


            Right actual = new JpaRightBuilder(originalRight)
                .name(name)
                .roles(roles)
                .build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaRight.class);

            assertThat(actual.getId()).isEqualTo(originalRight.getId());
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getRoles()).containsExactlyElementsOf(roles);

        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingAllSameValues() {
        assertDoesNotThrow(() -> {

            JpaRight originalRight = new JpaRight()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString())
                .setRoles(
                    Stream
                        .generate(() -> mock(JpaRole.class))
                        .limit(40)
                        .toList()
                );


            Right actual = new JpaRightBuilder(originalRight).build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaRight.class);

            assertThat(actual.getId()).isEqualTo(originalRight.getId());
            assertThat(actual.getName()).isEqualTo(originalRight.getName());
            assertThat(actual.getRoles()).containsExactlyElementsOf(originalRight.getRoles());

        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {

            String name = Generator.generateAString();
            List<JpaRole> roles = Stream
                .generate(() -> mock(JpaRole.class))
                .limit(Generator.randomInt(50, 500))
                .toList();


            Right actual = new JpaRightBuilder(null)
                .name(name)
                .roles(roles)
                .build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaRight.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getRoles()).containsExactlyElementsOf(roles);

        });
    }
}