package cmahy.plugins.architecture.application.rule;

import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public final class ApplicationArchRule {

    private ApplicationArchRule() {}

    public static final ArchRule APPLICATION_LAYER_HAS_TO_RESPECT_HEXAGONAL_ARCHITECTURE = layeredArchitecture()
        .consideringAllDependencies()
        .withOptionalLayers(true)

        .optionalLayer("adapter").definedBy("..adapter..")
        .layer("application").definedBy("..application..")

        .whereLayer("application").mayOnlyBeAccessedByLayers("adapter")

        .ignoreDependency(resideInAPackage("..exception.."), resideInAPackage("..application.."));

    public static final ArchRule ADAPTER_LAYER_HAS_TO_RESPECT_HEXAGONAL_ARCHITECTURE = layeredArchitecture()
        .consideringAllDependencies()

        .layer("adapter").definedBy("..adapter..")

        .whereLayer("adapter").mayNotBeAccessedByAnyLayer()

        .ignoreDependency(resideInAPackage("..exception.."), resideInAPackage("..adapter.."));

    public static final ArchRule NO_INJECTED_FIELDS = noFields()
        .should().beAnnotatedWith("org.springframework.beans.factory.annotation.Autowired")
        .orShould().beAnnotatedWith("jakarta.inject.Inject")
        .because("Only constructor should be annotated with injection");

    public static final ArchRule NO_SPRING_DEPENDENCY_INSIDE_APPLICATION = noClasses()
        .that().resideInAPackage("..application..")
        .should().accessClassesThat().resideInAPackage("..org.springframework..")
        .orShould().dependOnClassesThat().resideInAPackage("..org.springframework..");

    public static final ArchRule NO_SPRING_INJECTION_INSIDE_APPLICATION = noConstructors()
        .that().areDeclaredInClassesThat().resideInAPackage("..application..")
        .should().beAnnotatedWith("org.springframework.beans.factory.annotation.Autowired");

    public static final ArchRule NO_SPRING_BEAN_DECLARATION_FROM_APPLICATION = noClasses()
        .that().resideInAPackage("..application..")
        .and().areNotInterfaces()
        .should().beAnnotatedWith("org.springframework.stereotype.Controller")
        .orShould().beAnnotatedWith("org.springframework.stereotype.Component")
        .orShould().beAnnotatedWith("org.springframework.stereotype.Indexed")
        .orShould().beAnnotatedWith("org.springframework.stereotype.Repository")
        .orShould().beAnnotatedWith("org.springframework.stereotype.Service");

}
