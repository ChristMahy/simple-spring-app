package cmahy.webapp.user.adapter.jpa.entity.builder;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.adapter.jpa.entity.domain.JpaRole;
import cmahy.webapp.user.adapter.jpa.entity.domain.JpaUser;
import cmahy.webapp.user.kernel.domain.Role;
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

            Role actual = new JpaRoleBuilder()
                .name(name)
                .users(users)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaRole.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getUsers()).containsExactlyElementsOf(users);
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

            JpaRole originalRole = new JpaRole()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(300))
                .setUsers(
                    Stream
                        .generate(() -> mock(JpaUser.class))
                        .limit(40)
                        .toList()
                );

            Role actual = new JpaRoleBuilder(originalRole)
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
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {
            String newName = Generator.generateAString();
            List<JpaUser> newUsers = Stream
                .generate(() -> mock(JpaUser.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Role actual = new JpaRoleBuilder(null)
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