package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder.JpaRightBuilder;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRight;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JpaRightBuilderFactoryTest {

    @InjectMocks
    private JpaRightBuilderFactory jpaRightBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {


            JpaRightBuilder actual = jpaRightBuilderFactory.create();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaRightBuilder.class);

            JpaRight actualBuilt = actual.build();

            assertThat(actualBuilt).isNotNull();
        });
    }

    @Test
    void createWithOriginalRight() {
        assertDoesNotThrow(() -> {
            JpaRight right = mock(JpaRight.class, RETURNS_SELF);


            JpaRightBuilder actual = jpaRightBuilderFactory.create(right);


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaRightBuilder.class);

            JpaRight actualBuilt = actual.build();

            assertThat(actualBuilt)
                .isNotNull()
                .isSameAs(right);
        });
    }
}