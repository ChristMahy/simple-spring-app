package cmahy.plugins.architecture.application.rule;

import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public final class CommandArchRule {

    private static final String PACKAGE = "..application..command..";
    private static final String COMMAND_FQN = "cmahy.simple.spring.common.annotation.Command";

    private CommandArchRule() {}

    public static final ArchRule ONLY_CLASSES_WITH_COMMAND_ANNOTATION_ARE_ALLOWED = classes()
        .that().resideInAPackage(PACKAGE)
        .should().beAnnotatedWith(COMMAND_FQN);

    public static final ArchRule ALL_CLASSES_SHOULD_END_WITH_COMMAND_SUFFIX = classes()
        .that().resideInAPackage(PACKAGE)
        .should().haveSimpleNameEndingWith("Command");

    public static final ArchRule ONLY_COMMAND_ANNOTATION_IS_ALLOWED_IN_RIGHT_PACKAGE = noClasses()
        .that().areAnnotatedWith(COMMAND_FQN)
        .should().resideOutsideOfPackage(PACKAGE);

    public static final ArchRule COMMAND_ANNOTATION_IS_FORBIDDEN_WHEN_NO_IMPLEMENTATION = noClasses()
        .should().beAnnotatedWith(COMMAND_FQN)
        .andShould().haveSimpleNameNotEndingWith("Command");

}
