package cmahy.plugins.architecture.application.rule;

import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public final class QueryArchRule {

    private static final String PACKAGE = "..application..query..";
    private static final String QUERY_FQN = "cmahy.simple.spring.common.annotation.Query";

    private QueryArchRule() {}

    public static final ArchRule ONLY_CLASSES_WITH_QUERY_ANNOTATION_ARE_ALLOWED = classes()
        .that().resideInAPackage(PACKAGE)
        .should().beAnnotatedWith(QUERY_FQN);

    public static final ArchRule ALL_CLASSES_SHOULD_END_WITH_QUERY_SUFFIX = classes()
        .that().resideInAPackage(PACKAGE)
        .should().haveSimpleNameEndingWith("Query");

    public static final ArchRule ONLY_QUERY_ANNOTATION_IS_ALLOWED_IN_RIGHT_PACKAGE = noClasses()
        .that().areAnnotatedWith(QUERY_FQN)
        .should().resideOutsideOfPackage(PACKAGE);

    public static final ArchRule QUERY_ANNOTATION_IS_FORBIDDEN_WHEN_NO_IMPLEMENTATION = noClasses()
        .should().beAnnotatedWith(QUERY_FQN)
        .andShould().haveSimpleNameNotEndingWith("Query");

}
