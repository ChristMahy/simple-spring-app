package cmahy.webapp.user.kernel.domain.test;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.domain.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class RoleStubTest {

    @Test
    void roleNewInstance_whenAllFieldsComplete_thenFieldsReturnValue() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();
            String name = Generator.generateAString();
            Collection<UserStub> users = Stream
                .generate(() -> mock(UserStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Role actual = new RoleStub()
                .setId(id)
                .setName(name)
                .setUsers(users);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RoleStub.class);

            assertThat(actual.getId()).isEqualTo(id);
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getUsers()).containsExactlyElementsOf(users);
        });
    }

    @Test
    void roleNewInstance_whenAllFieldsEmpty_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            Role actual = new RoleStub();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RoleStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isNull();
            assertThat(actual.getUsers()).isNull();
        });
    }

    @Test
    void roleNewInstance_whenUseSetterToOverrideValueToNull_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();
            String name = Generator.generateAString();
            Collection<UserStub> users = Stream
                .generate(() -> mock(UserStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            RoleStub actual = new RoleStub()
                .setId(id)
                .setName(name)
                .setUsers(users);

            actual = actual
                .setId(null)
                .setName(null)
                .setUsers(null);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RoleStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isNull();
            assertThat(actual.getUsers()).isNull();
        });
    }
}