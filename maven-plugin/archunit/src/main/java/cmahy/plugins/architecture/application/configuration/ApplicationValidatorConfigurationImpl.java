package cmahy.plugins.architecture.application.configuration;

import cmahy.plugins.architecture.configuration.ValidatorConfigurationImpl;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

public class ApplicationValidatorConfigurationImpl extends ValidatorConfigurationImpl implements ApplicationValidatorConfiguration {

    private List<ApplicationLayer> activePackages = List.of(ApplicationLayer.application, ApplicationLayer.adapter);

    private Optional<CommandConfigurationImpl> command = Optional.empty();
    private Optional<QueryConfigurationImpl> query = Optional.empty();

    public List<ApplicationLayer> getActivePackages() {
        return activePackages;
    }

    public ApplicationValidatorConfigurationImpl setActivePackages(List<ApplicationLayer> activePackages) {
        this.activePackages = activePackages;
        return this;
    }

    public Optional<CommandConfigurationImpl> getCommand() {
        return command;
    }

    public ApplicationValidatorConfigurationImpl setCommand(CommandConfigurationImpl command) {
        this.command = Optional.ofNullable(command);
        return this;
    }

    public Optional<QueryConfigurationImpl> getQuery() {
        return query;
    }

    public ApplicationValidatorConfigurationImpl setQuery(QueryConfigurationImpl query) {
        this.query = Optional.ofNullable(query);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("type", getType())
            .append("command", getCommand().orElse(null))
            .append("query", getQuery().orElse(null))
            .toString();
    }

    @Override
    public Collection<ApplicationLayer> activePackages() {
        return this.getActivePackages();
    }

    @Override
    public Optional<? extends CommandConfiguration> command() {
        return this.getCommand();
    }

    @Override
    public Optional<? extends QueryConfiguration> query() {
        return this.getQuery();
    }

}