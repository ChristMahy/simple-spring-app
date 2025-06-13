package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.*;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class JpaRoleBuilderTest {

    @Test
    void build() {
        assertDoesNotThrow(() -> {

            String name = Generator.generateAString();
            List<JpaUser> users = Stream
                .generate(() -> mock(JpaUser.class))
                .limit(Generator.randomInt(50, 500))
                .toList();
            List<JpaRight> rights = Stream
                .generate(() -> mock(JpaRight.class))
                .limit(Generator.randomInt(50, 500))
                .toList();


            Role actual = new JpaRoleBuilder()
                .name(name)
                .users(users)
                .rights(rights)
                .build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaRole.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getUsers()).containsExactlyElementsOf(users);
            assertThat(actual.getRights()).containsExactlyElementsOf(rights);

        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {
            String newName = Generator.generateAString();
            List<JpaUser> newUsers = Stream
                .generate(() -> mock(JpaUser.class))
                .limit(Generator.randomInt(50, 500))
                .toList();
            List<JpaRight> newRights = Stream
                .generate(() -> mock(JpaRight.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            JpaRole originalRole = new JpaRole()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(300))
                .setUsers(
                    Stream
                        .generate(() -> mock(JpaUser.class))
                        .limit(40)
                        .toList()
                )
                .setRights(
                    Stream
                        .generate(() -> mock(JpaRight.class))
                        .limit(40)
                        .toList()
                );


            Role actual = new JpaRoleBuilder(originalRole)
                .name(newName)
                .users(newUsers)
                .rights(newRights)
                .build();


            assertThat(actual)
                .isNotNull()
                .isSameAs(originalRole);

            assertThat(actual.getId()).isEqualTo(originalRole.getId());
            assertThat(actual.getName()).isEqualTo(newName);
            assertThat(actual.getUsers()).containsExactlyElementsOf(newUsers);
            assertThat(actual.getRights()).containsExactlyElementsOf(newRights);

        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingAllSameValues() {
        assertDoesNotThrow(() -> {

            JpaRole originalRole = new JpaRole()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(300))
                .setUsers(
                    Stream
                        .generate(() -> mock(JpaUser.class))
                        .limit(40)
                        .toList()
                )
                .setRights(
                    Stream
                        .generate(() -> mock(JpaRight.class))
                        .limit(40)
                        .toList()
                );


            Role actual = new JpaRoleBuilder(originalRole).build();


            assertThat(actual)
                .isNotNull()
                .isSameAs(originalRole);

            assertThat(actual.getId()).isEqualTo(originalRole.getId());
            assertThat(actual.getName()).isEqualTo(originalRole.getName());
            assertThat(actual.getUsers()).containsExactlyElementsOf(originalRole.getUsers());
            assertThat(actual.getRights()).containsExactlyElementsOf(originalRole.getRights());

        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {

            String newName = Generator.generateAString();
            List<JpaUser> newUsers = Stream
                .generate(() -> mock(JpaUser.class))
                .limit(Generator.randomInt(50, 500))
                .toList();
            List<JpaRight> rights = Stream
                .generate(() -> mock(JpaRight.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Role actual = new JpaRoleBuilder(null)
                .name(newName)
                .users(newUsers)
                .rights(rights)
                .build();

            assertThat(actual).isNotNull();

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(newName);
            assertThat(actual.getUsers()).containsExactlyElementsOf(newUsers);
            assertThat(actual.getRights()).containsExactlyElementsOf(rights);

        });
    }
}