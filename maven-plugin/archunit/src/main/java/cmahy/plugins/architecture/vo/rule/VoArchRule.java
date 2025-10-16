package cmahy.plugins.architecture.vo.rule;

import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public final class VoArchRule {

    private VoArchRule() {}

    public static final ArchRule ALL_FIELDS_OF_VO_HAVE_TO_BE_FINAL = classes()
        .that().haveSimpleNameEndingWith("Vo")
        .should().haveOnlyFinalFields()
        .allowEmptyShould(true);

}
