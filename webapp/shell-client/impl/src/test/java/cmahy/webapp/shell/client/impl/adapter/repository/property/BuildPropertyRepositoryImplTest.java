package cmahy.webapp.shell.client.impl.adapter.repository.property;

import cmahy.common.helper.Generator;
import cmahy.webapp.shell.client.impl.application.vo.property.BuildPropertyVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.info.BuildProperties;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BuildPropertyRepositoryImplTest {

    private BuildPropertyRepositoryImpl buildPropertyRepositoryImpl;

    @Test
    void buildProperty() {
        assertDoesNotThrow(() -> {
            Properties props = new Properties();

            props.putAll(Map.of(
                "group", Generator.generateAString(),
                "artifact", Generator.generateAString(),
                "project.name", Generator.generateAString(),
                "name", Generator.generateAString(),
                "version", Generator.generateAString(),
                "time", String.valueOf(Instant.now().toEpochMilli()),
                "java.version", Generator.generateAString(),
                "description", Generator.generateAString()
            ));

            BuildProperties buildProperties = new BuildProperties(props);

            buildPropertyRepositoryImpl = new BuildPropertyRepositoryImpl(
                Optional.of(buildProperties)
            );

            Optional<BuildPropertyVo> actual = buildPropertyRepositoryImpl.buildProperty();

            assertThat(actual).isNotEmpty();

            assertThat(actual.get().group()).isEqualTo(buildProperties.getGroup());
            assertThat(actual.get().artifact()).isEqualTo(buildProperties.getArtifact());
            assertThat(actual.get().projectName()).isEqualTo(buildProperties.get("project.name"));
            assertThat(actual.get().name()).isEqualTo(buildProperties.getName());
            assertThat(actual.get().version()).isEqualTo(buildProperties.getVersion());
            assertThat(actual.get().time()).isEqualTo(buildProperties.getTime());
            assertThat(actual.get().javaVersion()).isEqualTo(buildProperties.get("java.version"));
            assertThat(actual.get().description()).isEqualTo(buildProperties.get("description"));
        });
    }

    @Test
    void buildProperty_whenGivenValueIsNull_thenResultHasToBeEmpty() {
        assertDoesNotThrow(() -> {
            buildPropertyRepositoryImpl = new BuildPropertyRepositoryImpl(Optional.empty());

            Optional<BuildPropertyVo> actual = buildPropertyRepositoryImpl.buildProperty();

            assertThat(actual).isEmpty();
        });
    }
}