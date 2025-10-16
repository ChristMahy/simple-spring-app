package cmahy.plugins.architecture.rule;

import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public final class SpringArchRule {

    private SpringArchRule() {}

    public static final ArchRule NO_SPRING_BOOT_DEPENDENCY = noClasses()
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage("org.springframework.boot..");

}
