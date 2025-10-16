package cmahy.plugins.architecture.configuration;

import cmahy.plugins.architecture.application.configuration.ApplicationValidatorConfigurationImpl;
import cmahy.plugins.architecture.validator.ArchitectureType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;
import java.util.stream.Collectors;

public class ArchConfigurationImpl implements ArchConfiguration {

    private ProjectConfigurationImpl project;
    private List<ApplicationValidatorConfigurationImpl> validators;

    public ProjectConfigurationImpl getProject() {
        return project;
    }

    public ArchConfigurationImpl setProject(ProjectConfigurationImpl project) {
        this.project = project;
        return this;
    }

    public List<ApplicationValidatorConfigurationImpl> getValidators() {
        return validators;
    }

    public ArchConfigurationImpl setValidators(List<ApplicationValidatorConfigurationImpl> validators) {
        this.validators = validators;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("project", getProject())
            .append("validators", getValidators())
            .toString();
    }

    @Override
    public ProjectConfiguration project() {
        return this.getProject();
    }

    @Override
    public Collection<? extends ValidatorConfiguration> validators() {
        return this.getValidators();
    }

    @Override
    public Map<ArchitectureType, ValidatorConfiguration> validatorsGroupByType() {
        return Objects.isNull(this.getValidators()) || this.getValidators().isEmpty() ?
            Collections.emptyMap() :
            this.getValidators().stream().collect(Collectors.toMap(ValidatorConfigurationImpl::getType, v -> v));
    }

}