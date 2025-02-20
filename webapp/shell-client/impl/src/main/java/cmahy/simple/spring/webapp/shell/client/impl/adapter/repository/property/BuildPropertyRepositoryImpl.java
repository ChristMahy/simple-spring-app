package cmahy.simple.spring.webapp.shell.client.impl.adapter.repository.property;

import cmahy.simple.spring.webapp.shell.client.impl.application.repository.property.BuildPropertyRepository;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.BuildPropertyVo;
import jakarta.inject.Named;
import org.springframework.boot.info.BuildProperties;

import java.util.Optional;

@Named
public class BuildPropertyRepositoryImpl implements BuildPropertyRepository {

    private final Optional<BuildPropertyVo> buildPropertyVo;

    public BuildPropertyRepositoryImpl(Optional<BuildProperties> buildProperties) {
        buildPropertyVo = buildProperties
            .map(bp -> new BuildPropertyVo(
                bp.getGroup(),
                bp.getArtifact(),
                bp.get("project.name"),
                bp.getName(),
                bp.getVersion(),
                bp.getTime(),
                bp.get("java.version"),
                bp.get("description")
            ));
    }

    @Override
    public Optional<BuildPropertyVo> buildProperty() {
        return buildPropertyVo;
    }
}
