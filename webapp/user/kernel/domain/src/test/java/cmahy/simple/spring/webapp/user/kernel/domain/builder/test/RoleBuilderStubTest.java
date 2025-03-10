package cmahy.simple.spring.webapp.user.kernel.domain.builder.test;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.kernel.domain.*;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RoleBuilderStub;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class RoleBuilderStubTest {
    
    @Test
    void build() {
        assertDoesNotThrow(() -> {
            String name = Generator.generateAString();
            List<UserStub> users = Stream
                .generate(() -> mock(UserStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Role actual = new RoleBuilderStub()
                .name(name)
                .users(users)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RoleStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getUsers()).containsExactlyElementsOf(users);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {
            String newName = Generator.generateAString();
            List<UserStub> newUsers = Stream
                .generate(() -> mock(UserStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            RoleStub originalRole = new RoleStub()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(300))
                .setUsers(
                    Stream
                        .generate(() -> mock(UserStub.class))
                        .limit(40)
                        .toList()
                );

            Role actual = new RoleBuilderStub(originalRole)
                .name(newName)
                .users(newUsers)
                .build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(originalRole);

            assertThat(actual.getId()).isEqualTo(originalRole.getId());
            assertThat(actual.getName()).isEqualTo(newName);
            assertThat(actual.getUsers()).containsExactlyElementsOf(newUsers);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingAllSameValues() {
        assertDoesNotThrow(() -> {

            RoleStub originalRole = new RoleStub()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(300))
                .setUsers(
                    Stream
                        .generate(() -> mock(UserStub.class))
                        .limit(40)
                        .toList()
                );


            Role actual = new RoleBuilderStub(originalRole).build();


            assertThat(actual)
                .isNotNull()
                .isSameAs(originalRole);

            assertThat(actual.getId()).isEqualTo(originalRole.getId());
            assertThat(actual.getName()).isEqualTo(originalRole.getName());
            assertThat(actual.getUsers()).containsExactlyElementsOf(originalRole.getUsers());
        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {
            String newName = Generator.generateAString();
            List<UserStub> newUsers = Stream
                .generate(() -> mock(UserStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Role actual = new RoleBuilderStub(null)
                .name(newName)
                .users(newUsers)
                .build();

            assertThat(actual).isNotNull();

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(newName);
            assertThat(actual.getUsers()).containsExactlyElementsOf(newUsers);
        });
    }
}