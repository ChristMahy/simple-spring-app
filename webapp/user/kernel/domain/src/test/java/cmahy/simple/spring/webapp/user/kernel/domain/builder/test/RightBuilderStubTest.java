package cmahy.simple.spring.webapp.user.kernel.domain.builder.test;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.kernel.domain.*;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RightBuilderStub;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

public class RightBuilderStubTest {

    @Test
    void build() {
        assertDoesNotThrow(() -> {

            String name = Generator.generateAString();
            List<RoleStub> roles = Stream
                .generate(() -> mock(RoleStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();


            Right actual = new RightBuilderStub()
                .name(name)
                .roles(roles)
                .build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RightStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getRoles()).containsExactlyElementsOf(roles);

        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {

            String name = Generator.generateAString();
            List<RoleStub> roles = Stream
                .generate(() -> mock(RoleStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            RightStub originalRight = new RightStub()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString())
                .setRoles(
                    Stream
                        .generate(() -> mock(RoleStub.class))
                        .limit(40)
                        .toList()
                );


            Right actual = new RightBuilderStub(originalRight)
                .name(name)
                .roles(roles)
                .build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RightStub.class);

            assertThat(actual.getId()).isEqualTo(originalRight.getId());
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getRoles()).containsExactlyElementsOf(roles);

        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingAllSameValues() {
        assertDoesNotThrow(() -> {

            RightStub originalRight = new RightStub()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString())
                .setRoles(
                    Stream
                        .generate(() -> mock(RoleStub.class))
                        .limit(40)
                        .toList()
                );


            Right actual = new RightBuilderStub(originalRight).build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RightStub.class);

            assertThat(actual.getId()).isEqualTo(originalRight.getId());
            assertThat(actual.getName()).isEqualTo(originalRight.getName());
            assertThat(actual.getRoles()).containsExactlyElementsOf(originalRight.getRoles());

        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {

            String name = Generator.generateAString();
            List<RoleStub> roles = Stream
                .generate(() -> mock(RoleStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();


            Right actual = new RightBuilderStub(null)
                .name(name)
                .roles(roles)
                .build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RightStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getRoles()).containsExactlyElementsOf(roles);

        });
    }
}
