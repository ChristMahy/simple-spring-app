package cmahy.simple.spring.webapp.user.kernel.domain.test;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.kernel.domain.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class RightStubTest {

    @Test
    void rightNewInstance_whenAllFieldsComplete_thenFieldsReturnValue() {
        assertDoesNotThrow(() -> {
            UUID uuid = Generator.randomUUID();
            String name = Generator.generateAString();
            Collection<RoleStub> roles = Stream
                .generate(() -> mock(RoleStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();


            Right actual = new RightStub()
                .setId(uuid)
                .setName(name)
                .setRoles(roles);


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RightStub.class);

            assertThat(actual.getId()).isEqualTo(uuid);
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getRoles()).containsExactlyElementsOf(roles);
        });
    }

    @Test
    void rightNewInstance_whenAllFieldsEmpty_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {


            Right actual = new RightStub();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RightStub.class);


            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isNull();
            assertThat(actual.getRoles()).isNull();
        });
    }

    @Test
    void rightNewInstance_whenUseSetterToOverrideValueToNull_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            UUID uuid = Generator.randomUUID();
            String name = Generator.generateAString();
            Collection<RoleStub> roles = Stream
                .generate(() -> mock(RoleStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();


            RightStub actual = new RightStub()
                .setId(uuid)
                .setName(name)
                .setRoles(roles);

            actual
                .setId(null)
                .setName(null)
                .setRoles(null);


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RightStub.class);


            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isNull();
            assertThat(actual.getRoles()).isNull();
        });
    }
}
